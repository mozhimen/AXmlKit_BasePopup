package com.mozhimen.xmlk.popwink.basepopup.builder

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.kotlin.utilk.wrapper.UtilKRes
import com.mozhimen.xmlk.popwink.basepopup.R
import com.mozhimen.xmlk.popwink.basepopup.commons.IClearMemoryListener
import com.mozhimen.xmlk.popwink.basepopup.builder.mos.PopwinKBuilderConfig

/**
 * @ClassName QuickPopupBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:45
 * @Version 1.0
 */
class PopwinKBuilder(obj: Any) : IClearMemoryListener {

    private var _config: PopwinKBuilderConfig = PopwinKBuilderConfig.generateDefault()
    private var _popupHost: Any = obj
    private var _width = 0
    private var _height = 0

    fun getConfig(): PopwinKBuilderConfig {
        return _config
    }

    fun getPopupHost(): Any {
        return _popupHost
    }

    fun getWidth(): Int {
        return _width
    }

    fun getHeight(): Int {
        return _height
    }

    companion object {
        fun with(context: Context): PopwinKBuilder {
            return PopwinKBuilder(context)
        }

        fun with(fragment: Fragment): PopwinKBuilder {
            return PopwinKBuilder(fragment)
        }

        fun with(dialog: Dialog): PopwinKBuilder {
            return PopwinKBuilder(dialog)
        }
    }

    fun setContentViewIntResLayout(intResLayout: Int): PopwinKBuilder {
        _config.setContentViewLayoutId(intResLayout)
        return this
    }

    fun setWidth(width: Int): PopwinKBuilder {
        this._width = width
        return this
    }

    fun setHeight(height: Int): PopwinKBuilder {
        this._height = height
        return this
    }

    fun setConfig(quickPopwinKConfig: PopwinKBuilderConfig): PopwinKBuilder {
        if (quickPopwinKConfig != _config) {
            quickPopwinKConfig.setContentViewLayoutId(_config.getContentViewLayoutId())
        }
        _config = quickPopwinKConfig
        return this
    }

    fun build(): PopwinKBuilderDelegate {
        if (_popupHost is Context) {
            return PopwinKBuilderDelegate(_popupHost as Context, this)
        }
        if (_popupHost is Fragment) {
            return PopwinKBuilderDelegate(_popupHost as Fragment, this)
        }
        if (_popupHost is Dialog) {
            return PopwinKBuilderDelegate(_popupHost as Dialog, this)
        }
        throw NullPointerException(UtilKRes.getString_ofContext(R.string.base_popwink_host_destroyed))
    }

    fun show(): PopwinKBuilderDelegate {
        return show(null)
    }

    fun show(anchorView: View?): PopwinKBuilderDelegate {
        val quickPopup = build()
        quickPopup.showPopupWindow(anchorView)
        return quickPopup
    }

    fun show(x: Int, y: Int): PopwinKBuilderDelegate {
        val quickPopup = build()
        quickPopup.showPopupWindow(x, y)
        return quickPopup
    }

    override fun clear(destroy: Boolean) {
        _config.clear(destroy)
    }
}