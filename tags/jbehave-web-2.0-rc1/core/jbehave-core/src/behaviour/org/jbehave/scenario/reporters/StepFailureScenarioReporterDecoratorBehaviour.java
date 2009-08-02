package org.jbehave.scenario.reporters;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jbehave.scenario.definition.Blurb;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class StepFailureScenarioReporterDecoratorBehaviour {

	private ScenarioReporter delegate;
	private StepFailureScenarioReporterDecorator decorator;

	@Before
	public void createDecorator() {
		delegate = mock(ScenarioReporter.class);
		decorator = new StepFailureScenarioReporterDecorator(delegate);
	}

	@Test
	public void shouldJustDelegateAllReportingMethodsOtherThanFailure() {
		// Given
		Blurb blurb = new Blurb("Some blurb");

		// When
		decorator.beforeStory(blurb);
		decorator.beforeScenario("My scenario 1");
		decorator.successful("Given step 1.1");
		decorator.pending("When step 1.2");
		decorator.notPerformed("Then step 1.3");
		decorator.afterScenario();
		decorator.afterStory();

		// Then
		InOrder inOrder = inOrder(delegate);

		inOrder.verify(delegate).beforeStory(blurb);
		inOrder.verify(delegate).beforeScenario("My scenario 1");
		inOrder.verify(delegate).successful("Given step 1.1");
		inOrder.verify(delegate).pending("When step 1.2");
		inOrder.verify(delegate).notPerformed("Then step 1.3");
		inOrder.verify(delegate).afterScenario();
		inOrder.verify(delegate).afterStory();
	}

	@Test
	public void shouldProvideFailureCauseWithMessageDescribingStep() {
		// Given
		Throwable t = new IllegalArgumentException("World Peace for everyone");
		String stepAsString = "When I have a bad idea";

		// When
		decorator.failed(stepAsString, t);

		// Then
		verify(delegate).failed(
				eq(stepAsString),
				argThat(hasMessage(t.getMessage() + "\nduring step: '"
						+ stepAsString + "'")));
	}

	@Test
	public void shouldRethrowFailureCauseAfterStory() {
		// Given
		Throwable t = new IllegalArgumentException("World Peace for everyone");
		String stepAsString = "When I have a bad idea";
		decorator.failed(stepAsString, t);

		// When
		try {
			decorator.afterStory();
			fail("Should have rethrown exception");
		} catch (Throwable rethrown) {
			// Then
			assertThat(rethrown, hasMessage(t.getMessage() + "\nduring step: '"
					+ stepAsString + "'"));
		}
	}

	private Matcher<Throwable> hasMessage(final String string) {
		return new TypeSafeMatcher<Throwable>() {

			private Matcher<String> equalTo;

			@Override
			public boolean matchesSafely(Throwable t) {
				equalTo = equalTo(string);
				return equalTo.matches(t.getMessage());
			}

			public void describeTo(Description description) {
				description.appendText("Throwable with message: ")
						.appendDescriptionOf(equalTo);
			}
		};
	}

}
