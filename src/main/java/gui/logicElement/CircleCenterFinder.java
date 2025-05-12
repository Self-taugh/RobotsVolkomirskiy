package gui.logicElement;

import Structures.Point;

import java.util.ArrayList;
import java.util.List;

public class CircleCenterFinder {
    public static Point findCircleCenters(Point p1, Point p2, Point p3, double radius) {
        List<Point> centers = new ArrayList<>();

        // Вычисляем середины отрезков p1p2 и p2p3
        Point mid1 = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        Point mid2 = new Point((p2.x + p3.x) / 2, (p2.y + p3.y) / 2);

        // Вычисляем угловые коэффициенты перпендикуляров
        double slope1 = (p2.x - p1.x) / (p1.y - p2.y); // Перпендикуляр к p1p2
        double slope2 = (p3.x - p2.x) / (p2.y - p3.y); // Перпендикуляр к p2p3

        // Вычисляем координаты центра окружности
        double x = (mid2.y - mid1.y + slope1 * mid1.x - slope2 * mid2.x) / (slope1 - slope2);
        double y = mid1.y + slope1 * (x - mid1.x);

        return new Point(x, y);
    }
}