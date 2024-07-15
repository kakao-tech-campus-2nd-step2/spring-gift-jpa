package gift.exception;

public class IllegalStateExample {

    private boolean initialized = false;

    public void initialize() {
        initialized = true;
    }

    public void performAction() {
        if (!initialized) {
            throw new IllegalStateException("Object is not initialized yet");
        }
        System.out.println("Action performed");
    }

    public static void main(String[] args) {
        IllegalStateExample example = new IllegalStateExample();

        // example.performAction(); // 이 줄을 주석 해제하면 IllegalStateException이 발생합니다.

        example.initialize();
        example.performAction(); // 올바르게 초기화된 후 메서드 호출
    }
}