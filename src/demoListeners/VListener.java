package demoListeners;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class VListener implements VerifyListener {
	private Text t;
	public VListener(Text t) {
		this.t =t;
	}
	@Override
	public void verifyText(VerifyEvent e) {
		t.setText("verifyText called at: " + e.time);
		t.pack();
	}

}
