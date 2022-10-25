package devgraft.auth.domain;

import java.util.Arrays;
import java.util.Objects;

public enum DeviceOSType {
    WEB, ANDROID, IOS, NONE;

    public static DeviceOSType of(final String value) {
        return Arrays.stream(values())
                .filter(deviceOSType -> Objects.equals(deviceOSType.name(), value))
                .findFirst()
                .orElse(NONE);
    }
}
