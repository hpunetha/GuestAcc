package in.ac.iiitd.guestacc;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3594734);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction pd = onView(
                allOf(withText("Sign in with Google"),
                        childAtPosition(
                                allOf(withId(R.id.signIn),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0)));
        pd.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3596081);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction pd2 = onView(
                allOf(withText("Sign in with Google"),
                        childAtPosition(
                                allOf(withId(R.id.signIn),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0)));
        pd2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3597694);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radioStudent), withText("Student"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroupLoginType),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                0)),
                                0)));
        appCompatRadioButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3582610);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextFrom), withText("Check-in \nFri, Mar 23"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutParent),
                                        1),
                                0)));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTo), withText("Check-out \nSun, Mar 25"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutParent),
                                        1),
                                1)));
        appCompatEditText2.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatActionBtnMenu),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        1),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnCheckAvail), withText("Check Availability"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutParent),
                                        3),
                                0)));
        appCompatButton3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3572195);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.plus),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.plus),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.plus),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.minus),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.CardView")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3553841);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnCheckAvail), withText("Check Availability"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutParent),
                                        3),
                                0)));
        appCompatButton4.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3596809);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3592558);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText = onView(
                allOf(withId(R.id.editTextFrom), withText("Check-in \nSat, Mar 24"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutParent),
                                        1),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("Check-in  Sat, Mar 24")));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3569675);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.editTextTo), withText("Check-out \nMon, Mar 26"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutParent),
                                        1),
                                1),
                        isDisplayed()));
        editText2.check(matches(withText("Check-out  Mon, Mar 26")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
