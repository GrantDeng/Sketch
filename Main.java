import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Grant on 2016-06-14.
 */
public class Main
{

    public static void main(String[] args){
        Jmodel model = new Jmodel();
        Jview view = new Jview(model);
        view.addNotify();
        model.SetView(view);
    }

}
