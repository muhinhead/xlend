package com.xlend.gui;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorAdjustBuilder;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author Nick Mukhin
 */
public class FXutils {

    private static final double SCALE = 1.3; // коэффициент увеличения
    private static final double DURATION = 300; // время анимации в мс

    public static Node createButton(Class cls, String iconName, final Runnable action) {
        // загружаем картинку
        final ImageView node;
        node = new ImageView(new Image(cls.getResource(iconName).toString()));

        // создаём анимацию увеличения картинки      
        final ScaleTransition animationGrow = new ScaleTransition(Duration.valueOf("" + DURATION + "ms"), node);
        animationGrow.setToX(SCALE);
        animationGrow.setToY(SCALE);

        // и уменьшения
        final ScaleTransition animationShrink = new ScaleTransition(Duration.valueOf("" + DURATION + "ms"), node);
        animationShrink.setToX(1);
        animationShrink.setToY(1);

        // добавляем эффект отражения
        final Reflection effect = new Reflection();
//        node.setEffect(effect);

        final ColorAdjust effectPressed = ColorAdjustBuilder.create().brightness(-0.5d).build();
        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // в обработчике нажатия применяем эффект. Тут имеется следующая тонкость: это уже второй эффект для кнопки,
                // поэтому мы его выставляем не напрямую, а как input для первого эффекта
                effect.setInput(effectPressed);
                // создаём Timeline, который через 300 мс отключит затемнение.
                TimelineBuilder.create().keyFrames(new KeyFrame(Duration.valueOf("300ms"), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        effect.setInput(null);
                    }
                })).build().play();
                action.run();
            }
        });

        // обработчик нажатия мыши
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                action.run();
            }
        });
        // при наведении курсора мы запускаем анимацию увеличения кнопки
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                node.toFront();
                animationShrink.stop();
                animationGrow.playFromStart();
            }
        });
        // когда курсор сдвигается -- запускаем анимацию уменьшения
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                animationGrow.stop();
                animationShrink.playFromStart();
            }
        });
        return node;
    }
}
