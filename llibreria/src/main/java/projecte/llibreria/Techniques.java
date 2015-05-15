
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package projecte.llibreria;

import projecte.llibreria.attention.BounceAnimator;
import projecte.llibreria.attention.FlashAnimator;
import projecte.llibreria.attention.PulseAnimator;
import projecte.llibreria.attention.RubberBandAnimator;
import projecte.llibreria.attention.ShakeAnimator;
import projecte.llibreria.attention.StandUpAnimator;
import projecte.llibreria.attention.SwingAnimator;
import projecte.llibreria.attention.TadaAnimator;
import projecte.llibreria.attention.WaveAnimator;
import projecte.llibreria.attention.WobbleAnimator;
import projecte.llibreria.bouncing_entrances.BounceInAnimator;
import projecte.llibreria.bouncing_entrances.BounceInDownAnimator;
import projecte.llibreria.bouncing_entrances.BounceInLeftAnimator;
import projecte.llibreria.bouncing_entrances.BounceInRightAnimator;
import projecte.llibreria.bouncing_entrances.BounceInUpAnimator;
import projecte.llibreria.fading_entrances.FadeInAnimator;
import projecte.llibreria.fading_entrances.FadeInDownAnimator;
import projecte.llibreria.fading_entrances.FadeInLeftAnimator;
import projecte.llibreria.fading_entrances.FadeInRightAnimator;
import projecte.llibreria.fading_entrances.FadeInUpAnimator;
import projecte.llibreria.fading_exits.FadeOutAnimator;
import projecte.llibreria.fading_exits.FadeOutDownAnimator;
import projecte.llibreria.fading_exits.FadeOutLeftAnimator;
import projecte.llibreria.fading_exits.FadeOutRightAnimator;
import projecte.llibreria.fading_exits.FadeOutUpAnimator;
import projecte.llibreria.flippers.FlipInXAnimator;
import projecte.llibreria.flippers.FlipInYAnimator;
import projecte.llibreria.flippers.FlipOutXAnimator;
import projecte.llibreria.flippers.FlipOutYAnimator;
import projecte.llibreria.rotating_entrances.RotateInAnimator;
import projecte.llibreria.rotating_entrances.RotateInDownLeftAnimator;
import projecte.llibreria.rotating_entrances.RotateInDownRightAnimator;
import projecte.llibreria.rotating_entrances.RotateInUpLeftAnimator;
import projecte.llibreria.rotating_entrances.RotateInUpRightAnimator;
import projecte.llibreria.rotating_exits.RotateOutAnimator;
import projecte.llibreria.rotating_exits.RotateOutDownLeftAnimator;
import projecte.llibreria.rotating_exits.RotateOutDownRightAnimator;
import projecte.llibreria.rotating_exits.RotateOutUpLeftAnimator;
import projecte.llibreria.rotating_exits.RotateOutUpRightAnimator;
import projecte.llibreria.sliders.SlideInDownAnimator;
import projecte.llibreria.sliders.SlideInLeftAnimator;
import projecte.llibreria.sliders.SlideInRightAnimator;
import projecte.llibreria.sliders.SlideInUpAnimator;
import projecte.llibreria.sliders.SlideOutDownAnimator;
import projecte.llibreria.sliders.SlideOutLeftAnimator;
import projecte.llibreria.sliders.SlideOutRightAnimator;
import projecte.llibreria.sliders.SlideOutUpAnimator;
import projecte.llibreria.zooming_entrances.ZoomInAnimator;
import projecte.llibreria.zooming_entrances.ZoomInDownAnimator;
import projecte.llibreria.zooming_entrances.ZoomInLeftAnimator;
import projecte.llibreria.zooming_entrances.ZoomInRightAnimator;
import projecte.llibreria.zooming_entrances.ZoomInUpAnimator;
import projecte.llibreria.zooming_exits.ZoomOutAnimator;
import projecte.llibreria.zooming_exits.ZoomOutDownAnimator;
import projecte.llibreria.zooming_exits.ZoomOutLeftAnimator;
import projecte.llibreria.zooming_exits.ZoomOutRightAnimator;
import projecte.llibreria.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {

    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);



    private Class animatorClazz;

    Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
