package nl.rdh.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

/**
 * @author r.de.haard
 *
 */
public class MetamodelProposalComputer implements IJavaCompletionProposalComputer {

	@Override
	public List<ICompletionProposal> computeCompletionProposals(final ContentAssistInvocationContext context,
			final IProgressMonitor progressMonitor) {

		// Was playing around to findout how this feels
		// if (isInDoubleQuot(context)) {
		return filterProposals(context, getProposals(context));
		// }

		// return Collections.emptyList();
	}

	private boolean isInDoubleQuot(final ContentAssistInvocationContext context) {
		try {
			final IDocument document = context.getDocument();
			final int invocationOffset = context.getInvocationOffset();
			final char charAtOffset = document.getChar(invocationOffset);

			// FIXME: also find the double quote before the current offset
			return '"' == charAtOffset;

		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
		return false;
	}

	private List<ICompletionProposal> filterProposals(final ContentAssistInvocationContext context,
			final List<ICompletionProposal> allProposals) {
		final List<ICompletionProposal> filteredProposals = new ArrayList<ICompletionProposal>();
		final String currentPrefix = getCurrentPrefix(context);

		// If there is no prefix return all;
		if (currentPrefix.length() == 0) {
			return allProposals;
		}

		// Detemine proposals based on prefix
		for (ICompletionProposal proposal : allProposals) {
			final String displayString = proposal.getDisplayString();
			if (displayString.toLowerCase().startsWith(currentPrefix.toLowerCase())) {
				filteredProposals.add(proposal);
			}
		}

		return filteredProposals;
	}

	private String getCurrentPrefix(final ContentAssistInvocationContext context) {
		String prefix = "";

		try {
			final IDocument document = context.getDocument();
			final int invocationOffset = context.getInvocationOffset();

			int offset = invocationOffset - 1;
			while (offset >= 0) {
				final char charAtOffset = document.getChar(offset);
				if ('"' == charAtOffset) {
					break;
				}

				prefix = charAtOffset + prefix;
				offset--;
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}

		return prefix;
	}

	private List<ICompletionProposal> getProposals(final ContentAssistInvocationContext context) {
		final List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		proposals.add(new CompletionProposal("AEntity1", context.getInvocationOffset(), 0, "AEntity1".length()));
		proposals.add(new CompletionProposal("AEntity2", context.getInvocationOffset(), 0, "AEntity2".length()));
		proposals.add(new CompletionProposal("BEntity1", context.getInvocationOffset(), 0, "BEntity1".length()));
		proposals.add(new CompletionProposal("CEntity1", context.getInvocationOffset(), 0, "CEntity1".length()));

		return proposals;
	}

	@Override
	public List<IContextInformation> computeContextInformation(final ContentAssistInvocationContext context,
			final IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sessionEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionStarted() {
		// TODO Auto-generated method stub

	}
}
