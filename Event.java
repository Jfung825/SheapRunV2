public class Event {
    public static boolean checkHit(Sheap sheap, Wave wave, int sheapSize, int waveHeight) {
        return (sheap.x + sheapSize > wave.x && sheap.x < wave.x + 40)
                && (sheap.y + sheapSize >= wave.y - waveHeight);
    }
}

