package de.cas.view.casUI.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.EditorSettings;
import de.cas.model.Automaton;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.menu.EditorMenuBar;
import de.cas.view.casUI.toolBar.EditorJToolbar;

public class AutomatonEditor extends JFrame implements CstmObserver{


	private static final long serialVersionUID = -5063769836062533375L;
	IAutomatonController controller;
	private EditorJToolbar toolbar;
	private EditorMenuBar menuBar;
	
	private RSyntaxTextArea rsyntaxTextArea;
	
	public AutomatonEditor(IAutomatonController controller){
		this.controller = controller;
		//this.controller.getAutomatonModel();
		this.addToObserverable();
		this.setLayout(new BorderLayout());
        
        this.toolbar = new EditorJToolbar(controller);
        this.menuBar = new EditorMenuBar(controller);
        
        rsyntaxTextArea = createHLSyntaxTextField();
        RTextScrollPane rtsp = new RTextScrollPane(rsyntaxTextArea);
        
        
        this.add(this.toolbar, BorderLayout.NORTH);
        this.add(rtsp,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
        this.update(null, this);

        this.pack();
	}
	
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(600,700);
    }
    
    private RSyntaxTextArea createHLSyntaxTextField(){
	      RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
	      textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
	      textArea.setCodeFoldingEnabled(true);

	      // A CompletionProvider is what knows of all possible completions, and
	      // analyzes the contents of the text area at the caret position to
	      // determine what completion choices should be presented. Most instances
	      // of CompletionProvider (such as DefaultCompletionProvider) are designed
	      // so that they can be shared among multiple text components.
	      CompletionProvider provider = createCompletionProvider();

	      // An AutoCompletion acts as a "middle-man" between a text component
	      // and a CompletionProvider. It manages any options associated with
	      // the auto-completion (the popup trigger key, whether to display a
	      // documentation window along with completion choices, etc.). Unlike
	      // CompletionProviders, instances of AutoCompletion cannot be shared
	      // among multiple text components.
	      AutoCompletion ac = new AutoCompletion(provider);
	      ac.install(textArea);

	      return textArea;
    }
    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private CompletionProvider createCompletionProvider() {

       // A DefaultCompletionProvider is the simplest concrete implementation
       // of CompletionProvider. This provider has no understanding of
       // language semantics. It simply checks the text entered up to the
       // caret position for a match against known completions. This is all
       // that is needed in the majority of cases.
       DefaultCompletionProvider provider = new DefaultCompletionProvider();

       // Add completions for all Java keywords. A BasicCompletion is just
       // a straightforward word completion.
       
       for (int i = 0; i < EditorSettings.basiccompletions.length; i++) {
    	   provider.addCompletion(new BasicCompletion(provider, EditorSettings.basiccompletions[i]));
       }
       for (int i = 0; i < EditorSettings.shorthandcompletions.size(); i++) {
    	   String[] value = EditorSettings.shorthandcompletions.get(i);
    	   if(value.length>2)
    	   {
    		   provider.addCompletion(new ShorthandCompletion(provider, value[0],value[1], value[2]));
    	   }
       }
       
       return provider;
    }

	public EditorJToolbar getToolbar() {
		return toolbar;
	}

	public RSyntaxTextArea getRsyntaxTextArea() {
		return rsyntaxTextArea;
	}

	@Override
	public void update(CstmObservable o, Object arg) {
		this.setTitle("Editor["+Automaton.getRunningAutomatons().get(this.controller.getAutomatonModel())+"]");  
	}

	@Override
	public void removeFromObserverable() {
		this.controller.getAutomatonModel().deleteObserver(this);
	}

	@Override
	public void addToObserverable() {
		this.controller.getView().getObservers().add(this);
		this.controller.getAutomatonModel().addObserver(this);
	}
    
    
}
