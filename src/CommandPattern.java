import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class CommandPattern {
    public static void main(String[] args) {
        Message mess = new Message();
        mess.init();
    }
}

abstract class Command {
    public Message mess;
    private String text;

    Command(Message mess) {
        this.mess = mess;
    }

    void text() {
        text = mess.textField.getText();
    }
    public abstract boolean execute();
}
class SendCommand extends Command {
    public SendCommand(Message mess) {
        super(mess);
    }
    @Override
    public boolean execute() {
        String s = mess.textField.getText();
        mess.textField.setText(null);
        mess.textField.append("Message ' " + s +  " ' is sent ");
        return false;
    }
}
class DeleteCommand extends Command {
    public DeleteCommand(Message mess) {
        super(mess);
    }
    @Override
    public boolean execute() {
        text();
        mess.textField.setText(null);
        mess.textField.append("Message is deleted ");
        return true;
    }
}
class CommandHistory {
    private Stack<Command> history = new Stack<>();
    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.pop();
    }
    public boolean isEmpty() {
        return history.isEmpty();
    }
}
class Message{
    public JTextArea textField;
    private CommandHistory history = new CommandHistory();
    public void init() {
        JFrame frame = new JFrame("Message");
        JPanel content = new JPanel();
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        textField = new JTextArea();
        textField.setLineWrap(true);
        content.add(textField);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton enter = new JButton("Enter");
        JButton del = new JButton("Delete");
        Message mess = this;
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(new SendCommand(mess));
            }
        }
        );
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(new DeleteCommand(mess));
            }
        }
        );
        buttons.add(enter);
        buttons.add(del);
        content.add(buttons);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void executeCommand(Command c) {
        if (c.execute()) {
            history.push(c);
        }
    }
}
