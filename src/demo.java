import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import demoListeners.*;

public class demo {

	public static void main(String[] args) {
		
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (new GridLayout());
		
		Label l = new Label(shell, SWT.NONE);
		l.setText("MultiCheckSelectionCombo");
		// the combo
		MultiCheckSelectionCombo c = new MultiCheckSelectionCombo(shell, SWT.NONE);
		String[] inputs = {"aasdfasdfasdf", "basdfasdfasdf", "casdfasdfasf", "dasdfasdfasf", "easdfasdfasdf", "fasdfasdfasdf", "gasdfasdfasf", "hasdfasdfasdf"};
		for (String s : inputs) {
			c.add(s);
		}
		// pack shell when combo selection display changes and resizes
		c.addModifyListener(e-> {
			shell.pack();
		});
		
		// group demo output
		Group g = new Group(shell, SWT.BORDER);
		g.setLayout(new GridLayout(2, false));
		g.setText("Demo");
		
		demoAdd(g, c);
		demoListeners(g, c);
		demoGetItem(g,c);
		demoDeselect(g, c);
		
		demoGetSelection(shell, g, c);
		
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		
	}

	private static void demoAdd(Group g, MultiCheckSelectionCombo c) {
		Button b = new Button(g, SWT.BUTTON1);
		b.setText("Add item");
		Composite inputs = new Composite(g, SWT.NONE);
		inputs.setLayout(new RowLayout());
		Text string = new Text(inputs, SWT.BORDER);
		string.setText("Item          ");
		Text index = new Text(inputs, SWT.BORDER);
		index.setText("Index");
		Combo selection = new Combo(inputs, SWT.BORDER);
		selection.setText("Selection");
		selection.add("True");
		selection.add("False");
		b.addListener(SWT.MouseDown, e-> {
			Integer i = null;
			try {
				i = Integer.parseInt(index.getText());
			} catch (NumberFormatException n) {
			}
			if (selection.getSelectionIndex() != -1 && i != null) {
				c.add(string.getText(), (selection.getSelectionIndex()==0), i);
			} else if (selection.getSelectionIndex() != -1) {
				c.add(string.getText(), (selection.getSelectionIndex()==0));
			} else if (i != null) {
				c.add(string.getText(), i);
			} else c.add(string.getText());
		});
	}
	
	private static void demoListeners(Group g, MultiCheckSelectionCombo c) {
		Button mb = new Button(g, SWT.TOGGLE);
		mb.setText("Toggle Modify Listener");
		Text mt = new Text(g, SWT.BORDER | SWT.READ_ONLY);
		mt.setText("deactivated");
		MListener ml = new MListener(mt);
		mb.addListener(SWT.Selection, e-> {
			if (mb.getSelection()) {
				c.addModifyListener(ml);
				mt.setText("activated");
			} else {
				c.removeModifyListener(ml);
				mt.setText("deactivated");
			}
		});
		Button sb = new Button(g, SWT.TOGGLE);
		sb.setText("Toggle Selection Listener");
		Text st = new Text(g, SWT.BORDER | SWT.READ_ONLY);
		st.setText("deactivated");
		SListener sl = new SListener(st);
		sb.addListener(SWT.Selection, e-> {
			if (sb.getSelection()) {
				c.addSelectionListener(sl);
				st.setText("activated");
			} else {
				c.removeSelectionListener(sl);
				st.setText("deactivated");
			}
		});
		Button vb = new Button(g, SWT.TOGGLE);
		vb.setText("Toggle Verify Listener");
		Text vt = new Text(g, SWT.BORDER | SWT.READ_ONLY);
		vt.setText("deactivated");
		VListener vl = new VListener(vt);
		vb.addListener(SWT.Selection, e-> {
			if (vb.getSelection()) {
				c.addVerifyListener(vl);
				vt.setText("activated");
			} else {
				c.removeVerifyListener(vl);
				vt.setText("deactivated");
			}
		});
	}
	
	private static void demoDeselect(Group g, MultiCheckSelectionCombo c) {
		Button db = new Button(g, SWT.BUTTON1);
		db.setText("Deselect");
		Text index = new Text(g, SWT.BORDER);
		index.setText("Index");
		db.addListener(SWT.MouseDown, e-> {
			Integer i = null;
			try {
				i = Integer.parseInt(index.getText());
			} catch (NumberFormatException n) {
			}
			if (i != null) {
				c.deselect(i);;
			}
		});
		
		Button dab = new Button(g, SWT.BUTTON1);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		dab.setLayoutData(gd);
		dab.setText("Deselect all");
		dab.addListener(SWT.MouseDown, e-> {
			c.deselectAll();
		});
		
	}
	
	private static void demoGetItem(Group g, MultiCheckSelectionCombo c) {
		Button get = new Button(g, SWT.BUTTON1);
		get.setText("Get Item");
		Composite io = new Composite(g, SWT.NONE);
		io.setLayout(new RowLayout());
		Text index = new Text(io, SWT.BORDER);
		index.setText("Index");
		Text item = new Text(io, SWT.BORDER | SWT.READ_ONLY);
		item.setText("                ");
		get.addListener(SWT.MouseDown, e-> {
			try {
				int i = Integer.parseInt(index.getText());
				item.setText(c.getItem(i));
			} catch (NumberFormatException n) {}
		});
		
		Button count = new Button(g, SWT.BUTTON1);
		count.setText("Get Item Count");
		Text output = new Text(g, SWT.BORDER | SWT.READ_ONLY);
		output.setText("     ");
		count.addListener(SWT.MouseDown, e-> {
			output.setText(String.valueOf(c.getItemCount()));
		});
	}
	
	private static void demoGetSelection(Shell shell, Group g, MultiCheckSelectionCombo c) {
		Button b = new Button(g, SWT.BUTTON1);
		b.setText("Get Selections");
		Text t = new Text(g, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		t.setLayoutData(new GridData(GridData.FILL_BOTH));
		t.setText("Output here:");
		b.addListener(SWT.MouseDown, e -> {
			String[] output = c.getSelections();
			if (output.length > 0) {
				StringBuilder sb = new StringBuilder("Selected items: {");
				sb.append(output[0]);
				for (int i = 1; i < output.length; i++) {
					sb.append(", " + output[i]);
				}
				sb.append("}");
				t.setText(sb.toString());
			} else t.setText("None Selected");
		});
		
		Button ib = new Button(g, SWT.BUTTON1);
		ib.setText("Get Selection Indices");
		Text it = new Text(g, SWT.BORDER);
		it.setText("Output here:");
		ib.addListener(SWT.MouseDown, e -> {
			int[] output = c.getSelectionIndices();
			if (output.length > 0) {
				StringBuilder sb = new StringBuilder("Selected indices: {");
				sb.append(output[0]);
				for (int i = 1; i < output.length; i++) {
					sb.append(", " + output[i]);
				}
				sb.append("}");
				it.setText(sb.toString());
			} else it.setText("None Selected");
			shell.pack();
		});
		}

}
