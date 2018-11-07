import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.*;

/**
 * Created by Grant on 2016-06-18.
 */
public class Jview2 extends JPanel
{
    Jmodel model;
    Point start, end;
    public String action;

    int inselect = 0;
    Point re_start, re_end;
    int re_type;
    Color re_color, re_fill;


    public void setAction(String a){
        action = a;
    }

    public Jview2(Jmodel m)
    {
        model = m;

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                if (action == "selection") {
                    for (int i = model.shapes.size() - 1; i >= 0; i--)
                    {
                        if (model.shapes.get(i).intersects(e.getX(),e.getY(), 7, 7))
                        {
                            model.select = i;
                            if (model.shape_fill.get(i) == null)
                            {
                                model.color1 = model.shape_color.get(i);
                            } else {
                                model.color1 = model.shape_fill.get(i);
                            }
                            model.update_color();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
                start = new Point(e.getX(), e.getY());
                end = start;
                if (action == "selection" && model.select != -1){
                    re_start = model.shape_start.get(model.select);
                    re_end = model.shape_end.get(model.select);
                    re_type = model.shape_type.get(model.select);
                    re_color = model.shape_color.get(model.select);
                    re_fill = model.shape_fill.get(model.select);
                    model.remove(model.select);
                    model.select = -1;
                    inselect = 1;

                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                super.mouseReleased(e);

                if (action == "rect")
                {
                    Shape rect_shape = draw_rect(start.x, e.getX(), start.y, e.getY());
                    model.add(rect_shape, start, e.getPoint(), 1);
                } else if (action == "circle") {
                    Shape circle_shape = draw_circle(start.x, e.getX(), start.y, e.getY());
                    model.add(circle_shape, start, e.getPoint(), 2);
                } else if (action == "line"){
                    Shape line_shape = draw_line(start.x, e.getX(), start.y, e.getY());
                    model.add(line_shape, start, e.getPoint(), 3);
                } else if (action == "erase") {
                    for (int i = model.shapes.size() - 1; i >= 0; i--)
                    {
                        if (model.shapes.get(i).intersects(e.getX(),e.getY(), 7, 7))
                        {
                            model.remove(i);
                            break;
                        }
                    }
                } else if (action == "fill") {
                    for (int i = model.shapes.size() - 1; i >= 0; i--)
                    {
                        if (model.shapes.get(i).intersects(e.getX(),e.getY(), 7, 7))
                        {
                            model.shape_fill.set(i, model.color1);
                            break;
                        }
                    }
                } else if (inselect == 1) {
                    int x_change = e.getX() - start.x;
                    int y_change = e.getY() - start.y;
                    re_start.x += x_change;
                    re_start.y += y_change;
                    re_end.x += x_change;
                    re_end.y += y_change;
                    model.select = model.shapes.size();
                    if (re_type == 1){
                        model.add(draw_rect(re_start.x, re_end.x, re_start.y, re_end.y), re_start, re_end, 1);
                        model.shape_color.set(model.select,re_color);
                        model.shape_fill.set(model.select,re_fill);
                    } else if (re_type == 2){
                        model.add(draw_circle(re_start.x, re_end.x, re_start.y, re_end.y), re_start, re_end, 2);
                        model.shape_color.set(model.select,re_color);
                        model.shape_fill.set(model.select,re_fill);
                    } else {
                        model.add(draw_line(re_start.x, re_end.x, re_start.y, re_end.y), re_start, re_end, 3);
                        model.shape_color.set(model.select,re_color);
                        model.shape_fill.set(model.select,re_fill);
                    }

                    inselect = 0;
                }
                start = null;
                end = null;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                end = new Point(e.getX(),e.getY());
                repaint();
            }
        });
    }

    public void update (){
        repaint();
    }

    @Override
    public void paint (Graphics g)
    {
        Graphics2D g2= (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        for (int i = 0; i < model.shapes.size(); i++)
        {
            if (i == model.select)
            {
                final float dash1[] = {10.0f};
                final BasicStroke dashed =
                        new BasicStroke(10.0f,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f, dash1, 0.0f);
                g2.setStroke(dashed);
            } else
            {
                g2.setStroke(new BasicStroke(model.shape_stroke.get(i)));
            }
            g2.setColor(model.shape_color.get(i));
            g2.draw(model.shapes.get(i));
            if (model.shape_fill.get(i) == null){

            } else {
                g2.setColor(model.shape_fill.get(i));
                g2.fill(model.shapes.get(i));
            }
        }
        g2.setStroke(new BasicStroke(model.cur_stroke));
        if (start != null && end != null)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
            g2.setPaint(Color.LIGHT_GRAY);
            Shape temp;
            if (action == "circle"){
                temp = draw_circle(start.x, end.x, start.y, end.y);
                g2.draw(temp);
            } else if (action == "rect"){
                temp = draw_rect(start.x, end.x, start.y, end.y);
                g2.draw(temp);
            } else if (action == "line"){
                temp = draw_line(start.x, end.x, start.y, end.y);
                g2.draw(temp);
            } else if (inselect == 1){
                int x_change = end.x - start.x;
                int y_change = end.y - start.y;
                if (re_type == 1){
                    temp = draw_rect(re_start.x + x_change, re_end.x + x_change, re_start.y + y_change, re_end.y + y_change);
                } else if (re_type == 2){
                    temp = draw_circle(re_start.x + x_change, re_end.x + x_change, re_start.y + y_change, re_end.y + y_change);
                } else {
                    temp = draw_line(re_start.x + x_change, re_end.x + x_change, re_start.y + y_change, re_end.y + y_change);
                }
                final float dash1[] = {10.0f};
                final BasicStroke dashed =
                        new BasicStroke(10.0f,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f, dash1, 0.0f);
                g2.setStroke(dashed);
                g2.draw(temp);
            }
        }
    }

    private Rectangle2D.Float draw_rect(int x1, int x2, int y1, int y2)
    {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int high = Math.abs(x1 - x2);
        int wight = Math.abs(y1 - y2);
        return new Rectangle2D.Float(x, y, high, wight);
    }

    private Ellipse2D.Float draw_circle(int x1, int x2, int y1, int y2)
    {
        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;
        int high = Math.abs(x1 - x2);
        int wight = Math.abs(y1 - y2);
        int d = (int) Math.sqrt(Math.pow(high, 2) + Math.pow(wight,2));
        return new Ellipse2D.Float(x - d/2, y - d/2, d, d);
    }

    private Line2D.Float draw_line(int x1, int x2, int y1, int y2)
    {
        return new Line2D.Float(x1, y1, x2, y2);
    }


}
