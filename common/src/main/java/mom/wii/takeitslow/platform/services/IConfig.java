package mom.wii.takeitslow.platform.services;

public interface IConfig {
    boolean getAllowSwimming();
    void setAllowSwimming(boolean value);

    double getSwimSpeedScale();
    void setSwimSpeedScale(double value);

    boolean getAllowCreative();
    void setAllowCreative(boolean value);

    boolean getAllowFlying();
    void setAllowFlying(boolean value);
}
