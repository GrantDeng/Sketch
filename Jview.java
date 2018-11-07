import javafx.scene.input.MouseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.border.*;

public class Jview extends JPanel implements ActionListener, KeyListener
{
    public JToggleButton selection, erase, line, circle, rect, fill, show_current, t_s, t_m, t_l;
    Jmodel model;
    JFrame frame;
    Jview2 draw_area;
    Color Green = Color.GREEN;
    Color Blue = Color.BLUE;
    Color Yellow = Color.YELLOW;
    Color Pink = Color.PINK;
    Color Cyan = Color.CYAN;
    Color Red = Color.RED;

    @Override
    public void addNotify(){
        super.addNotify();
        requestFocus();
    }

    public Jview(Jmodel m)
    {
        model = m;
        frame = new JFrame("JSketch");
        frame.setPreferredSize(new Dimension(600, 600));
        frame.getContentPane().add(this);
        setLayout(new BorderLayout());
        setMenuBar();
        setToolPalette();

        draw_area = new Jview2(model);
        draw_area.addNotify();
        //draw_area.setMinimumSize(new Dimension(300, 300));
        draw_area.setBackground(Color.WHITE);
        //draw_area.setSize(new Dimension(300,300));

        JScrollPane d = new JScrollPane(draw_area);

        d.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        d.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        d.setBounds(50, 30, 300, 50);

        add(d, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void setMenuBar()
    {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("file");
        JMenu view = new JMenu("view");

        JMenuItem m_new = new JMenuItem("New");
        m_new.addActionListener(this);
        JMenuItem m_load = new JMenuItem("Load");
        m_load.addActionListener(this);
        JMenuItem m_save = new JMenuItem("Save");
        m_save.addActionListener(this);
        file.add(m_new);
        file.add(m_load);
        file.add(m_save);

        JMenuItem m_full = new JMenuItem("Full Size");
        m_full.addActionListener(this);
        JMenuItem m_fit = new JMenuItem("Fit to Window");
        m_fit.addActionListener(this);
        view.add(m_full);
        view.add(m_fit);

        menu.add(file);
        menu.add(view);
        frame.setJMenuBar(menu);
    }

    void setToolPalette()
    {
        JPanel tool = new JPanel();
        //tool.setLayout(new BorderLayout());
        tool.setBackground(Color.DARK_GRAY);
        Box vertBox = Box.createVerticalBox();

        Border border1 = new LineBorder(Color.black, 1);

        JPanel toolBox = new JPanel();
        toolBox.setLayout(new GridLayout(0, 2));
        Dimension tool_size = new Dimension(35, 35);

        selection = new JToggleButton(new ImageIcon("0.GIF"));
        selection.setPreferredSize(tool_size);
        selection.addActionListener(this);
        selection.setBorder(border1);
        selection.addKeyListener(this);
        toolBox.add(selection);

        erase = new JToggleButton(new ImageIcon("9.GIF"));
        erase.setPreferredSize(tool_size);
        erase.addActionListener(this);
        erase.setBorder(border1);
        toolBox.add(erase);

        line = new JToggleButton(new ImageIcon("13.GIF"));
        line.setPreferredSize(tool_size);
        line.addActionListener(this);
        line.setBorder(border1);
        toolBox.add(line);

        circle = new JToggleButton(new ImageIcon("15.GIF"));
        circle.setPreferredSize(tool_size);
        circle.addActionListener(this);
        circle.setBorder(border1);
        toolBox.add(circle);

        rect = new JToggleButton(new ImageIcon("14.GIF"));
        rect.setPreferredSize(tool_size);
        rect.addActionListener(this);
        rect.setBorder(border1);
        toolBox.add(rect);

        fill = new JToggleButton(new ImageIcon("1.GIF"));
        fill.setPreferredSize(tool_size);
        fill.addActionListener(this);
        fill.setBorder(border1);
        toolBox.add(fill);

        vertBox.add(toolBox);

        JPanel smallPanel = new JPanel();
        smallPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        smallPanel.setSize(200, 200);
        smallPanel.setBackground(Color.GRAY);
        vertBox.add(smallPanel);


        JPanel color = new JPanel();
        color.setLayout(new GridLayout(0, 2));

        JToggleButton current_col = new JToggleButton("current");
        current_col.setPreferredSize(new Dimension(50, 20));
        current_col.setBorder(border1);
        color.add(current_col);

        show_current = new JToggleButton();
        show_current.setPreferredSize(new Dimension(50, 20));
        Border border_cur_col = new LineBorder(model.color1, 10);
        show_current.setBorder(border_cur_col);
        color.add(show_current);

        Border green_border = new LineBorder(Color.GREEN, 10);
        JToggleButton green = new JToggleButton("green");
        green.setPreferredSize(new Dimension(50, 20));
        green.setBorder(green_border);
        green.addActionListener(this);
        green.addKeyListener(this);
        green.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 3){
                    Color cha = JColorChooser.showDialog(null, "pick", Color.black);
                    Green = cha;
                    green.setBorder(new LineBorder(Green, 10));
                }
            }
        });
        color.add(green);

        Border red_border = new LineBorder(Color.RED, 10);
        JToggleButton red = new JToggleButton("red");
        red.setPreferredSize(new Dimension(50, 20));
        red.setBorder(red_border);
        red.addActionListener(this);
        red.addKeyListener(this);
        red.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 3){
                    Color cha = JColorChooser.showDialog(null, "pick", Color.black);
                    Red = cha;
                    red.setBorder(new LineBorder(Red, 10));
                }
            }
        });
        color.add(red);

        Border blue_border = new LineBorder(Color.BLUE, 10);
        JToggleButton blue = new JToggleButton("blue");
        blue.setPreferredSize(new Dimension(50, 20));
        blue.setBorder(blue_border);
        blue.addActionListener(this);
        blue.addKeyListener(this);
        blue.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 3){
                    Color cha = JColorChooser.showDialog(null, "pick", Color.black);
                    Blue = cha;
                    blue.setBorder(new LineBorder(Blue, 10));
                }
            }
        });
        color.add(blue);

        Border yellow_border = new LineBorder(Color.YELLOW, 10);
        JToggleButton yellow = new JToggleButton("yellow");
        yellow.setPreferredSize(new Dimension(50, 20));
        yellow.setBorder(yellow_border);
        yellow.addActionListener(this);
        yellow.addKeyListener(this);
        yellow.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 3){
                    Color cha = JColorChooser.showDialog(null, "pick", Color.black);
                    Yellow = cha;
                    yellow.setBorder(new LineBorder(Yellow, 10));
                }
            }
        });
        color.add(yellow);

        Border pink_border = new LineBorder(Color.PINK, 10);
        JToggleButton pink = new JToggleButton("pink");
        pink.setPreferredSize(new Dimension(50, 20));
        pink.setBorder(pink_border);
        pink.addActionListener(this);
        pink.addKeyListener(this);
        pink.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 3){
                    Color cha = JColorChooser.showDialog(null, "pick", Color.black);
                    Pink = cha;
                    pink.setBorder(new LineBorder(Pink, 10));
                }
            }
        });
        color.add(pink);

        Border cyan_border = new LineBorder(Color.CYAN, 10);
        JToggleButton cyan = new JToggleButton("cyan");
        cyan.setPreferredSize(new Dimension(50, 20));
        cyan.setBorder(cyan_border);
        cyan.addActionListener(this);
        cyan.addKeyListener(this);
        cyan.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 3){
                    Color cha = JColorChooser.showDialog(null, "pick", Color.black);
                    Cyan = cha;
                    cyan.setBorder(new LineBorder(Cyan, 10));
                }
            }
        });
        color.add(cyan);

        vertBox.add(color);

        JPanel choose = new JPanel();
        JToggleButton choose_color = new JToggleButton("choose");
        choose_color.setPreferredSize(new Dimension(50, 20));
        choose_color.addActionListener(this);
        choose_color.addKeyListener(this);
        choose_color.setBorder(border1);
        choose.add(choose_color);
        vertBox.add(choose);

        JPanel smallPanel1 = new JPanel();
        smallPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        smallPanel1.setSize(200, 200);
        smallPanel1.setBackground(Color.GRAY);
        vertBox.add(smallPanel1);


        JPanel thick = new JPanel();
        thick.setLayout(new GridLayout(0, 1));

        t_s = new JToggleButton(new ImageIcon("118.png"));
        t_s.setPreferredSize(new Dimension(50, 40));
        t_s.addActionListener(this);
        t_s.addKeyListener(this);
        thick.add(t_s);

        t_m = new JToggleButton(new ImageIcon("117.png"));
        t_m.setPreferredSize(new Dimension(50, 40));
        t_m.addActionListener(this);
        t_m.addKeyListener(this);
        thick.add(t_m);

        Border border = new LineBorder(Color.red, 3);
        t_l = new JToggleButton(new ImageIcon("112.png"));
        t_l.setPreferredSize(new Dimension(50, 40));
        t_l.addActionListener(this);
        t_l.setBorder(border);
        t_l.addKeyListener(this);
        thick.add(t_l);

        vertBox.add(thick);
        tool.add(vertBox, BorderLayout.NORTH);

        add(tool, BorderLayout.WEST);
    }

    public void update_color(Color c){
        Border update = new LineBorder(model.color1, 10);
        show_current.setBorder(update);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == selection)
        {
            set_tool_border(selection);
            draw_area.setAction("selection");
        } else if (e.getSource() == erase) {
            set_tool_border(erase);
            model.select = -1;
            draw_area.repaint();
            draw_area.setAction("erase");
        } else if (e.getSource() == circle) {
            set_tool_border(circle);
            model.select = -1;
            draw_area.repaint();
            draw_area.setAction("circle");
        } else if (e.getSource() == rect) {
            set_tool_border(rect);
            model.select = -1;
            draw_area.repaint();
            draw_area.setAction("rect");
        } else if (e.getSource() == line) {
            set_tool_border(line);
            model.select = -1;
            draw_area.repaint();
            draw_area.setAction("line");
        } else if (e.getSource() == fill) {
            set_tool_border(fill);
            model.select = -1;
            draw_area.repaint();
            draw_area.setAction("fill");
        } else if (e.getActionCommand() == "choose")
        {
            model.color1 = JColorChooser.showDialog(null, "pick", Color.black);
            update_color(model.color1);
        } else if (e.getActionCommand() == "green"){
            model.color1 = Green;
            update_color(model.color1);
        } else if (e.getActionCommand() == "blue"){
            model.color1 = Blue;
            update_color(model.color1);
        } else if (e.getActionCommand() == "yellow"){
            model.color1 = Yellow;
            update_color(model.color1);
        } else if (e.getActionCommand() == "cyan"){
            model.color1 = Cyan;
            update_color(model.color1);
        } else if (e.getActionCommand() == "red"){
            model.color1 = Red;
            update_color(model.color1);
        } else if (e.getActionCommand() == "pink"){
            model.color1 = Pink;
            update_color(model.color1);
        } else if (e.getActionCommand() == "Save") {
            try
            {
                FileOutputStream fout = new FileOutputStream("save");
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.writeObject(model.shapes);
                oout.writeObject(model.shape_color);
                oout.writeObject(model.shape_fill);
                oout.writeObject(model.shape_stroke);
                oout.writeObject(model.shape_start);
                oout.writeObject(model.shape_end);
                oout.writeObject(model.shape_type);
                fout.close();
            } catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        } else if (e.getActionCommand() == "Load") {
            try
            {
                FileInputStream fin = new FileInputStream("save");
                ObjectInputStream oin = new ObjectInputStream(fin);
                model.shapes = (ArrayList<Shape>) oin.readObject();
                model.shape_color = (ArrayList<Color>) oin.readObject();
                model.shape_fill = (ArrayList<Color>) oin.readObject();
                model.shape_stroke = (ArrayList<Integer>) oin.readObject();
                model.shape_start = (ArrayList<Point>) oin.readObject();
                model.shape_end = (ArrayList<Point>) oin.readObject();
                model.shape_type = (ArrayList<Integer>) oin.readObject();
                model.select = -1;
                draw_area.update();
            } catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
        } else if (e.getActionCommand() == "New") {
            model.shapes.clear();
            model.shape_color.clear();
            model.shape_fill.clear();
            draw_area.update();
            model.select = -1;
        } else if (e.getSource() == t_l){
            model.cur_stroke = 12;
            set_thickness_border(t_l);
        } else if (e.getSource() == t_m){
            model.cur_stroke = 8;
            set_thickness_border(t_m);
        } else if (e.getSource() == t_s){
            model.cur_stroke = 4;
            set_thickness_border(t_s);
        }

    }

    void set_thickness_border(JToggleButton b)
    {
        Border border = new LineBorder(Color.red, 3);
        if (t_s == b){
            t_s.setBorder(border);
        } else {
            t_s.setBorder(null);
        }
        if (t_m == b){
            t_m.setBorder(border);
        } else {
            t_m.setBorder(null);
        }
        if (t_l == b){
            t_l.setBorder(border);
        } else {
            t_l.setBorder(null);
        }
    }

    void set_tool_border(JToggleButton b){
        Border border1 = new LineBorder(Color.black, 1);
        Border border = new LineBorder(Color.red, 3);
        if (selection == b){
            selection.setBorder(border);
        } else {
            selection.setBorder(border1);
        }
        if (erase == b){
            erase.setBorder(border);
        } else {
            erase.setBorder(border1);
        }
        if (line == b){
            line.setBorder(border);
        } else {
            line.setBorder(border1);
        }
        if (fill == b){
            fill.setBorder(border);
        } else {
            fill.setBorder(border1);
        }
        if (circle == b){
            circle.setBorder(border);
        } else {
            circle.setBorder(border1);
        }
        if (rect == b){
            rect.setBorder(border);
        } else {
            rect.setBorder(border1);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == 27)
        {
            model.select = -1;
            draw_area.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
