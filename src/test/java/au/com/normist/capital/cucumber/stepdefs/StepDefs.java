package au.com.normist.capital.cucumber.stepdefs;

import au.com.normist.capital.CapitalappApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CapitalappApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
