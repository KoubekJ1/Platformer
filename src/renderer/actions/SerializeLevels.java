package renderer.actions;

import util.Level1;
import util.Level2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class SerializeLevels extends AbstractAction {

    public SerializeLevels() {
        super("Serialize levels");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serializeLevels();
    }

    public static void serializeLevels() {
        try {
            // To prevent bugs related to the classes being modified,
            Level1.getLevel().serialize("official");
            Level2.getLevel().serialize("official");
            JOptionPane.showMessageDialog(null, "Serialization successful!", "Serialize levels", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Unable to serialize levels!\n\n" + ex.getClass() + "\n" + ex.getMessage() + "\n\nPlease download the serialized levels from GitHub in the \"assets/levels/official/\" folder.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
