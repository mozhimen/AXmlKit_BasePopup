package com.mozhimen.xmlk.popwink.basepopup.builder

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.kotlin.utilk.android.util.e
import com.mozhimen.xmlk.popwink.basepopup.bases.BasePopwinK
import com.mozhimen.xmlk.popwink.basepopup.cons.CFlag
import com.mozhimen.xmlk.popwink.basepopup.builder.commons.PopwinKBuilderOnClickCallback
import com.mozhimen.xmlk.popwink.basepopup.builder.mos.PopwinKBuilderConfig
import java.lang.reflect.InvocationTargetException

/**
 * @ClassName QuickPopup
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 21:54
 * @Version 1.0
 */
class PopwinKBuilderDelegate : BasePopwinK {
    private var _config: PopwinKBuilderConfig
    private var _builder: PopwinKBuilder

    constructor(fragment: Fragment, builder: PopwinKBuilder) : super(fragment, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    constructor(dialog: Dialog, builder: PopwinKBuilder) : super(dialog, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    constructor(context: Context, builder: PopwinKBuilder) : super(context, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    fun getConfig(): PopwinKBuilderConfig {
        return _config
    }

    fun getBuilder(): PopwinKBuilder {
        return _builder
    }

    private fun applyConfigSetting(config: PopwinKBuilderConfig) {
        if (config.getPopupBlurOption() != null) {
            setBlurOption(config.getPopupBlurOption())
        } else {
            setBlurBackgroundEnable(config.getFlag() and CFlag.BLUR_BACKGROUND != 0, config.getOnBlurOptionInitListener())
        }
        isPopupFadeEnable = config.getFlag() and CFlag.FADE_ENABLE != 0
        for ((methodName, value) in config.getInvokeParams()) {
            val method = config.getMethod(methodName)
            if (method != null) {
                try {
                    method.invoke(this, value)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                    e.message?.e(TAG)
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                    e.message?.e(TAG)
                }
            }
        }
        performClick()
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        applyConfigSetting(_config)
    }

    override fun onDestroy() {
        _builder.clear(true)
        super.onDestroy()
    }

    private fun performClick() {
        val eventsMap = _config.getListenersHolderMap()
        if (eventsMap == null || eventsMap.isEmpty()) return
        for ((viewId, event) in eventsMap) {
            val view: View? = findViewById(viewId)
            if (view != null) {
                if (event.second) {
                    view.setOnClickListener { v ->
                        if (event.first != null) {
                            if (event.first is PopwinKBuilderOnClickCallback) {
                                (event.first as PopwinKBuilderOnClickCallback).popwinKBuilderDelegate = this@PopwinKBuilderDelegate
                            }
                            event.first!!.onClick(v)
                        }
                        dismiss()
                    }
                } else view.setOnClickListener(event.first)
            }
        }
    }
}