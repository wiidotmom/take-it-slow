package mom.wii.takeitslow.platform.services;

public interface IConfig {
    boolean getEnabled();
    void setEnabled(boolean enabled);

    boolean getAllowSwimming();
    void setAllowSwimming(boolean value);

    double getSwimSpeedScale();
    void setSwimSpeedScale(double value);

    boolean getAllowCreative();
    void setAllowCreative(boolean value);

    boolean getAllowFlying();
    void setAllowFlying(boolean value);

    boolean getAllowMounted();
    void setAllowMounted(boolean value);
}
