import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Grant on 2016-06-14.
 */
public class Jmodel
{
    Jview view;
    Color color1 = Color.black;
    Integer cur_stroke = 12;
    int select = -1;
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    ArrayList<Color> shape_color = new ArrayList<Color>();
    ArrayList<Color> shape_fill = new ArrayList<Color>();
    ArrayList<Integer> shape_stroke = new ArrayList<Integer>();
    ArrayList<Point> shape_start = new ArrayList<Point>();
    ArrayList<Point> shape_end = new ArrayList<Point>();
    ArrayList<Integer> shape_type = new ArrayList<Integer>();

    public void SetView(Jview v)
    {
        view = v;
    }


    public void update_color(){
        view.update_color(color1);
    }

    public void add(Shape s, Point st, Point en, int type)
    {
        shapes.add(s);
        shape_color.add(color1);
        shape_fill.add(null);
        shape_stroke.add(cur_stroke);
        shape_start.add(st);
        shape_end.add(en);
        shape_type.add(type);
    }

    public void remove(int i){
        shapes.remove(i);
        shape_color.remove(i);
        shape_fill.remove(i);
        shape_stroke.remove(i);
        shape_start.remove(i);
        shape_end.remove(i);
        shape_type.remove(i);
    }
}
