import codedraw.CodeDraw;
import java.awt.*;

public class PhotoApp1 {


    /*
    1. Datenkapselung --> Das Zusammenfassen von Variablen und Methoden zu einer Einheit (Klasse)
                           z.B. die ganzen Klassen sind Beispiele für Datenkapselung
    2. Data-Hiding --> dass man nur die Methoden und Variablen von außen sichtbar macht, die man wirklich auch von außen aufrufen können soll.
                        wir haben zum Beispiel die Variablen alle auf private gesetzt
    3.  SimpleRasterRGB.getPixelColor() --> statische Methode. erkennt man daran, dass der Methodenaufruf direkt nach dem Namen der Klasse steht, nzw. dass der erste Buchstabe groß geschrieben ist.
        r1.getPixelColor() --> Objektmethode. Man muss zuerst eine Instanz des Objekts erzeugen. Variablen fangen normalerweise mit einem kleinen Buchstaben an.
    4. this referenziert immer auf die aktuelle Instanz des Objekts.
    5. die statische Methode führt die Berechnung auf dem mitgegebenen Objekt aus, während die andere Methode auf der Objektinstanz ausgeführt wird
    6. Ja, man kann darauf zugreifen.

     */

    public static void main(String[] args) {

        // TODO: change this method according to 'Aufgabenblatt1.md'.
        SimpleRasterRGB simpleRasterRGB = new SimpleRasterRGB(40, 60);
        simpleRasterRGB.drawLine(0, 1, 35, 9, new Color(20, 25, 250));
        simpleRasterRGB.drawLine(30, 5, 0, 30, Color.ORANGE);
        simpleRasterRGB.drawLine(2, 0, 7, 40, Color.GREEN);
        draw(simpleRasterRGB);

        SimpleRasterRGB simpleRasterRGB1 = SimpleRasterRGB.convolve(simpleRasterRGB, new double[][]{
                {0.077847, 0.123317, 0.077847},
                {0.123317, 0.195346, 0.123317},
                {0.077847, 0.123317, 0.077847}
        });
        draw(simpleRasterRGB1);

        SimpleRasterRGB newOne = simpleRasterRGB.addBottom(30, Color.GREEN);
        draw(newOne);

    }

    // Draws the image with fixed pixel size in a new window.
    public static void draw(SimpleRasterRGB simpleRasterRGB) {

        // TODO: change this method according to 'Aufgabenblatt1.md'.
        int cellSize = 10;
        CodeDraw cd = new CodeDraw(simpleRasterRGB.getWidth() * cellSize, simpleRasterRGB.getHeight() * cellSize);

        // draw a square of size 'cellSize' for each pixel
        for (int j = 0; j < simpleRasterRGB.getHeight(); j++) {
            for (int i = 0; i < simpleRasterRGB.getWidth(); i++) {
                int x = i * cellSize;
                int y = j * cellSize;
                cd.setColor(simpleRasterRGB.getPixelColor(i, j));
                cd.fillSquare(x, y, cellSize);
            }
        }
        cd.show();
    }
}

