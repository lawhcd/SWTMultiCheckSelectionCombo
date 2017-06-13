package demoListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

public class SListener implements SelectionListener {
	private Text t;
	public SListener(Text t) {
		this.t =t;
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		t.setText("widgetSelected called at: " + e.time);
		t.getShell().pack();
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		t.setText("widgetDefaultSelected called at: " + e.time);
		t.getShell().pack();
	}

}
