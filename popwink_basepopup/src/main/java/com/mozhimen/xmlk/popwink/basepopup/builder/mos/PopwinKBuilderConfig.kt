package com.mozhimen.xmlk.popwink.basepopup.builder.mos

import android.animation.Animator
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import com.mozhimen.animk.builder.impls.AnimationScaleType
import com.mozhimen.kotlin.elemk.android.os.cons.CVersCode
import com.mozhimen.blurk.mos.BlurKConfig
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.kotlin.obj2clazz
import com.mozhimen.xmlk.popwink.basepopup.bases.BasePopwinK
import com.mozhimen.xmlk.popwink.basepopup.builder.PopwinKBuilderDelegate
import com.mozhimen.xmlk.popwink.basepopup.commons.IClearMemoryListener
import com.mozhimen.xmlk.popwink.basepopup.cons.CFlag
import java.lang.reflect.Method

/**
 * @ClassName QuickPopupConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:23
 * @Version 1.0
 */
class PopwinKBuilderConfig : IClearMemoryListener {

    companion object {

        @JvmStatic
        fun generateDefault(): PopwinKBuilderConfig {
            return PopwinKBuilderConfig()
                .setShowAnimation(AnimationScaleType.CENTER_SHOW.build())
                .setDismissAnimation(AnimationScaleType.CENTER_HIDE.build())
                .setFadeInAndOut(UtilKBuildVersion.get_SDK_INT() != CVersCode.V_23_6_M)
        }
    }

    private val _invokeMap: MutableMap<String, Method> = HashMap()
    private var _listenersHolderMap: HashMap<Int, Pair<View.OnClickListener, Boolean>>? = null
    private var _invokeParams: MutableMap<String, Any> = HashMap()

    @Volatile
    private var _destroyed = false
    private var _flag = CFlag.IDLE
    private var _contentViewLayoutId = 0
    private var _onBlurOptionInitListener: BasePopwinK.OnBlurOptionInitListener? = null
    private var _bitmapBlurOption: BlurKConfig? = null

    init {
        if (UtilKBuildVersion.get_SDK_INT() == CVersCode.V_23_6_M) {
            _flag = _flag and CFlag.FADE_ENABLE.inv()
        }
    }

    fun getMethod(name: String): Method? {
        return if (_invokeMap.containsKey(name)) _invokeMap[name] else null
    }

    fun getListenersHolderMap(): HashMap<Int, Pair<View.OnClickListener, Boolean>>? {
        return _listenersHolderMap
    }

    fun getInvokeParams(): MutableMap<String, Any> {
        return _invokeParams
    }

    fun isDestroyed(): Boolean {
        return _destroyed
    }

    fun getFlag(): Int {
        return _flag
    }

    fun getContentViewLayoutId(): Int {
        return _contentViewLayoutId
    }

    fun getOnBlurOptionInitListener(): BasePopwinK.OnBlurOptionInitListener? {
        return _onBlurOptionInitListener
    }

    fun getPopupBlurOption(): BlurKConfig? {
        return _bitmapBlurOption
    }

    fun setShowAnimation(showAnimation: Animation): PopwinKBuilderConfig {
        set("setShowAnimation", showAnimation)
        return this
    }

    fun setDismissAnimation(dismissAnimation: Animation): PopwinKBuilderConfig {
        set("setDismissAnimation", dismissAnimation)
        return this
    }

    fun setShowAnimator(showAnimator: Animator): PopwinKBuilderConfig {
        set("setShowAnimator", showAnimator)
        return this
    }

    fun setDismissAnimator(dismissAnimator: Animator): PopwinKBuilderConfig {
        set("setDismissAnimator", dismissAnimator)
        return this
    }

    fun setOnDismissListener(dismissListener: BasePopwinK.OnDismissListener): PopwinKBuilderConfig {
        set("setOnDismissListener", dismissListener)
        return this
    }

    fun setBlurBackground(blurBackground: Boolean): PopwinKBuilderConfig {
        return setBlurBackground(blurBackground, null)
    }

    fun setBlurBackground(blurBackground: Boolean, mInitListener: BasePopwinK.OnBlurOptionInitListener?): PopwinKBuilderConfig {
        setFlag(CFlag.BLUR_BACKGROUND, blurBackground)
        _onBlurOptionInitListener = mInitListener
        return this
    }

    fun setBlurOption(bitmapBlurOption: BlurKConfig): PopwinKBuilderConfig {
        _bitmapBlurOption = bitmapBlurOption
        return this
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener): PopwinKBuilderConfig {
        return setOnClickListener(viewId, listener, false)
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener, dismissWhenClick: Boolean): PopwinKBuilderConfig {
        if (_listenersHolderMap == null) {
            _listenersHolderMap = HashMap()
        }
        _listenersHolderMap!![viewId] = Pair.create(listener, dismissWhenClick)
        return this
    }

    fun setFadeInAndOut(fadeEnable: Boolean): PopwinKBuilderConfig {
        setFlag(CFlag.FADE_ENABLE, fadeEnable)
        return this
    }

    fun setOffsetX(offsetX: Int): PopwinKBuilderConfig {
        set("setOffsetX", offsetX)
        return this
    }

    fun setMaskOffsetX(offsetX: Int): PopwinKBuilderConfig {
        set("setMaskOffsetX", offsetX)
        return this
    }


    fun setOffsetY(offsetY: Int): PopwinKBuilderConfig {
        set("setOffsetY", offsetY)
        return this
    }

    fun setMaskOffsetY(offsetY: Int): PopwinKBuilderConfig {
        set("setMaskOffsetY", offsetY)
        return this
    }

    fun setOverlayStatusbarMode(mode: Int): PopwinKBuilderConfig {
        set("setOverlayStatusbarMode", mode)
        return this
    }

    fun setOverlayNavigationBarMode(mode: Int): PopwinKBuilderConfig {
        set("setOverlayNavigationBarMode", mode)
        return this
    }

    fun setOverlayStatusbar(overlay: Boolean): PopwinKBuilderConfig {
        set("setOverlayStatusbar", overlay)
        return this
    }

    fun setOverlayNavigationBar(overlay: Boolean): PopwinKBuilderConfig {
        set("setOverlayNavigationBar", overlay)
        return this
    }

    fun setAlignBackground(alignBackground: Boolean): PopwinKBuilderConfig {
        set("setAlignBackground", alignBackground)
        return this
    }

    fun setAlignBackgroundGravity(gravity: Int): PopwinKBuilderConfig {
        set("setAlignBackgroundGravity", gravity)
        return this
    }

    fun setAutoMirrorEnable(autoMirrorEnable: Boolean): PopwinKBuilderConfig {
        set("setAutoMirrorEnable", autoMirrorEnable)
        return this
    }

    fun setBackground(background: Drawable): PopwinKBuilderConfig {
        set("setBackground", background)
        return this
    }

    fun setBackgroundColor(color: Int): PopwinKBuilderConfig {
        return setBackground(ColorDrawable(color))
    }

    fun setPopupGravity(gravity: Int): PopwinKBuilderConfig {
        set("setPopupGravity", gravity)
        return this
    }

    fun setClipChildren(clipChildren: Boolean): PopwinKBuilderConfig {
        set("setClipChildren", clipChildren)
        return this
    }

    fun setOutSideTouchable(outSideTouchable: Boolean): PopwinKBuilderConfig {
        set("setOutSideTouchable", outSideTouchable)
        return this
    }

    fun setLinkTo(linkedView: View): PopwinKBuilderConfig {
        set("setLinkTo", linkedView)
        return this
    }

    fun setContentViewLayoutId(contentViewLayoutId: Int): PopwinKBuilderConfig {
        this._contentViewLayoutId = contentViewLayoutId
        return this
    }

    fun setMinWidth(minWidth: Int): PopwinKBuilderConfig {
        set("setMinWidth", minWidth)
        return this
    }

    fun setMaxWidth(maxWidth: Int): PopwinKBuilderConfig {
        set("setMaxWidth", maxWidth)
        return this
    }

    fun setMinHeight(minHeight: Int): PopwinKBuilderConfig {
        set("setMinHeight", minHeight)
        return this
    }

    fun setMaxHeight(maxHeight: Int): PopwinKBuilderConfig {
        set("setMaxHeight", maxHeight)
        return this
    }

    fun setBackPressEnable(enable: Boolean): PopwinKBuilderConfig {
        set("setBackPressEnable", enable)
        return this
    }

    fun setOutSideDismiss(outsideDismiss: Boolean): PopwinKBuilderConfig {
        set("setOutSideDismiss", outsideDismiss)
        return this
    }

    fun setKeyEventListener(keyEventListener: BasePopwinK.KeyEventListener): PopwinKBuilderConfig {
        set("setKeyEventListener", keyEventListener)
        return this
    }

    fun setOnKeyboardChangeListener(listener: Function2<Rect, Boolean, Unit>): PopwinKBuilderConfig {
        set("setOnKeyboardChangeListener", listener)
        return this
    }

    override fun clear(destroy: Boolean) {
        _destroyed = true
        _bitmapBlurOption?.clear()
        _onBlurOptionInitListener = null
        _listenersHolderMap?.clear()
        _listenersHolderMap = null
        _invokeParams.clear()
    }

    private fun appendInvokeMap(name: String, paramClass: Class<*>?): Boolean {
        if (_invokeMap.containsKey(name)) return true
        val method = findMethod(name, paramClass)
        if (method != null) {
            _invokeMap[name] = method
            return true
        }
        return false
    }

    private fun findMethod(methodName: String, parameterTypes: Class<*>?): Method? {
        return try {
            PopwinKBuilderDelegate::class.java.getMethod(methodName, parameterTypes)
        } catch (e: Exception) {
            UtilKLogWrapper.e("not found", methodName + " " + parameterTypes!!.name)
            null
        }
    }

    private fun set(name: String, obj: Any) {
        if (appendInvokeMap(name, obj.obj2clazz()))
            _invokeParams[name] = obj
    }

    private fun setFlag(flag: Int, added: Boolean) {
        if (!added) {
            this._flag = this._flag and flag.inv()
        } else {
            this._flag = this._flag or flag
        }
    }
}