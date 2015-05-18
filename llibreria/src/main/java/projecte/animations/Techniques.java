
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

package projecte.animations;

import projecte.animations.attention.BounceAnimator;
import projecte.animations.attention.FlashAnimator;
import projecte.animations.attention.PulseAnimator;
import projecte.animations.attention.RubberBandAnimator;
import projecte.animations.attention.ShakeAnimator;
import projecte.animations.attention.StandUpAnimator;
import projecte.animations.attention.SwingAnimator;
import projecte.animations.attention.TadaAnimator;
import projecte.animations.attention.WaveAnimator;
import projecte.animations.attention.WobbleAnimator;
import projecte.animations.bouncing_entrances.BounceInAnimator;
import projecte.animations.bouncing_entrances.BounceInDownAnimator;
import projecte.animations.bouncing_entrances.BounceInLeftAnimator;
import projecte.animations.bouncing_entrances.BounceInRightAnimator;
import projecte.animations.bouncing_entrances.BounceInUpAnimator;
import projecte.animations.fading_entrances.FadeInAnimator;
import projecte.animations.fading_entrances.FadeInDownAnimator;
import projecte.animations.fading_entrances.FadeInLeftAnimator;
import projecte.animations.fading_entrances.FadeInRightAnimator;
import projecte.animations.fading_entrances.FadeInUpAnimator;
import projecte.animations.fading_exits.FadeOutAnimator;
import projecte.animations.fading_exits.FadeOutDownAnimator;
import projecte.animations.fading_exits.FadeOutLeftAnimator;
import projecte.animations.fading_exits.FadeOutRightAnimator;
import projecte.animations.fading_exits.FadeOutUpAnimator;
import projecte.animations.flippers.FlipInXAnimator;
import projecte.animations.flippers.FlipInYAnimator;
import projecte.animations.flippers.FlipOutXAnimator;
import projecte.animations.flippers.FlipOutYAnimator;
import projecte.animations.rotating_entrances.RotateInAnimator;
import projecte.animations.rotating_entrances.RotateInDownLeftAnimator;
import projecte.animations.rotating_entrances.RotateInDownRightAnimator;
import projecte.animations.rotating_entrances.RotateInUpLeftAnimator;
import projecte.animations.rotating_entrances.RotateInUpRightAnimator;
import projecte.animations.rotating_exits.RotateOutAnimator;
import projecte.animations.rotating_exits.RotateOutDownLeftAnimator;
import projecte.animations.rotating_exits.RotateOutDownRightAnimator;
import projecte.animations.rotating_exits.RotateOutUpLeftAnimator;
import projecte.animations.rotating_exits.RotateOutUpRightAnimator;
import projecte.animations.sliders.SlideInDownAnimator;
import projecte.animations.sliders.SlideInLeftAnimator;
import projecte.animations.sliders.SlideInRightAnimator;
import projecte.animations.sliders.SlideInUpAnimator;
import projecte.animations.sliders.SlideOutDownAnimator;
import projecte.animations.sliders.SlideOutLeftAnimator;
import projecte.animations.sliders.SlideOutRightAnimator;
import projecte.animations.sliders.SlideOutUpAnimator;
import projecte.animations.zooming_entrances.ZoomInAnimator;
import projecte.animations.zooming_entrances.ZoomInDownAnimator;
import projecte.animations.zooming_entrances.ZoomInLeftAnimator;
import projecte.animations.zooming_entrances.ZoomInRightAnimator;
import projecte.animations.zooming_entrances.ZoomInUpAnimator;
import projecte.animations.zooming_exits.ZoomOutAnimator;
import projecte.animations.zooming_exits.ZoomOutDownAnimator;
import projecte.animations.zooming_exits.ZoomOutLeftAnimator;
import projecte.animations.zooming_exits.ZoomOutRightAnimator;
import projecte.animations.zooming_exits.ZoomOutUpAnimator;

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
