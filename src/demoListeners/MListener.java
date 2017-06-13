package demoListeners;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

public class MListener implements ModifyListener {
	private Text t;
	public MListener(Text t) {
		this.t =t;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		t.setText("ModifyText called at: " + e.time);
		t.pack();
	}
	
}