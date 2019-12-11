package bowling;

import bowling.domain.Bowling;
import bowling.domain.Frame;
import bowling.view.ResultView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class BowlingTest {

    @Test
    @DisplayName("볼링 객체를 통해 프레임 생성 여부 테스트")
    void containsFrameByBowlingTest() {
        Bowling bowling = new Bowling();
        bowling.go(5);
        Frame frame = Frame.frame(5);
        assertThat(bowling.getFrames().contains(frame)).isTrue();
    }

    @Test
    @DisplayName("다음 프레임 생성 테스트")
    void createNextFrameTest() {
        Bowling bowling = new Bowling();
        bowling.go(10);
        bowling.go(5);
        bowling.go(3);
        bowling.go(6);
        bowling.go(4);
        bowling.go(2);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(0);
        bowling.go(1);
        bowling.go(2);
        bowling.go(5);
        bowling.go(5);
        bowling.go(10);

        ResultView.printBowling(bowling, "KSJ");
        assertThat(bowling.getCurrentFrameNumber()).isEqualTo(9);
    }

    @Test
    @DisplayName("두번째 투구에서 점수가 10보다 클 때 예외 처리")
    void validateCreateFrameByOverHitPinTest() {
        Bowling bowling = new Bowling();
        bowling.go(7);
        assertThatIllegalArgumentException().isThrownBy(() -> {
            bowling.go(4);
        });
    }

    @Test
    @DisplayName("현재 프레임 테스트")
    void checkOfCurrentFrame() {
        Bowling bowling = new Bowling();
        assertThat(bowling.getCurrentFrameNumber()).isEqualTo(0);
        bowling.go(10);
        assertThat(bowling.getCurrentFrameNumber()).isEqualTo(1L);

        bowling.go(5);
        bowling.go(4);
        assertThat(bowling.getCurrentFrameNumber()).isEqualTo(2L);
    }
}