package ocp;

public enum OCShowType {

    BLOCKED, ELSEWHERE, FREE, TENTATIVE;

    public static OCShowType parse(final String showType) {
        if (showType == null || showType.isBlank()) {
            return FREE;
        }
        switch (Integer.parseInt(showType)) {//TODO
        case 1:
            return TENTATIVE;
        case 2:
            return BLOCKED;
        case 3:
            return FREE;
        case 4:
            return ELSEWHERE;
        }
        return FREE;
    }

}
