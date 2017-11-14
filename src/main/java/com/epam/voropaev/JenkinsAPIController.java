package com.epam.voropaev;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class JenkinsAPIController {

    private static JenkinsServer jenkins;

    public static JenkinsServer getJenkinsConnectionInstance() throws URISyntaxException {
        if(jenkins != null){
            return jenkins;
        }
        else {
            return init();
        }
    }

    private JenkinsAPIController(){

    }

    private static JenkinsServer init() throws URISyntaxException {
        jenkins = new JenkinsServer(new URI("http://10.6.99.14:8081"), "voropa", "ovoro7732");
        return jenkins;
    }

    public static Map<String,Job> getListOFJobs() throws URISyntaxException, IOException {
        Map<String, Job> jobs;
        jobs = getJenkinsConnectionInstance().getJobs();
        return jobs;
    }

    public  static Job getJobByName(String  name) throws URISyntaxException, IOException {
       return getJenkinsConnectionInstance().getJob(name);
    }

    public static void buildJobByName(String name) throws URISyntaxException, IOException {
        getJenkinsConnectionInstance().getJob(name).build(true);
    }

    public  static void buildDefaultJob() throws URISyntaxException, IOException {
        getJenkinsConnectionInstance().getJob("GmailAutomationFramework MultiJob").build(true);
    }
}
