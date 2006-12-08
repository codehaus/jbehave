/*
 * Created on 17-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package example.currency;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public class SwingCurrencyConverterView implements CurrencyConverterView {

	private JFrame frame;
    private JTextField amountField;
    private JButton toGBPButton;
    private JButton fromGBPButton;
    private JLabel total;
	private JList list;

	public SwingCurrencyConverterView() {
        frame = new JFrame();
		frame.setTitle("Currency Converter");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(5, 2));
        JLabel currencyLabel = new JLabel("Other Currency");
        contentPane.add(currencyLabel);
		list = new JList(new Currency [] {Currency.USD, Currency.EUR});
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contentPane.add(list);
        JLabel amountLabel = new JLabel("Amount");
        contentPane.add(amountLabel);
        amountField = new JTextField(3);
        contentPane.add(amountField);
        toGBPButton = new JButton("Convert To GBP");
        contentPane.add(toGBPButton);
        fromGBPButton = new JButton("Convert From GBP");
        contentPane.add(fromGBPButton);
        JLabel converted = new JLabel("Converted Total");
        contentPane.add(converted);
        total = new JLabel("Total");
        contentPane.add(total);
    }

    public void show() {
		Dimension frameSize = new Dimension(400, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(frameSize);
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

	public void registerEventHandler(final CurrencyConverterViewEventHandler eventHandler) {
    	toGBPButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Currency currency = (Currency)list.getSelectedValue();
				eventHandler.handleConvertToSterling(currency, amountField.getText());
			}
		});

		fromGBPButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Currency currency = (Currency)list.getSelectedValue();
				eventHandler.handleConvertFromSterling(currency, amountField.getText());
			}
		});

		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                eventHandler.handleClose();
            }
        });
	}

	public void conversionResult(double result) {
     	total.setText(Double.toString(result));
	}

	public void displayError(InvalidAmountException error) {
    	JOptionPane.showMessageDialog(frame, error.getMessage());
	}

	public void dispose() {
        frame.dispose();
	}
}
