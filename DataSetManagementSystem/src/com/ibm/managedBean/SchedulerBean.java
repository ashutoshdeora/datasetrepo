package com.ibm.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.ibm.scheduler.SchedulerJob;

@ManagedBean
@ApplicationScoped
public class SchedulerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Scheduler scheduler;

	private List<QuartzJob> quartzJobList = new ArrayList<QuartzJob>();

	public SchedulerBean()  {
		try{
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();

		// Get QuartzInitializerListener
		StdSchedulerFactory stdSchedulerFactory = (StdSchedulerFactory) servletContext
				.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
		
		StdSchedulerFactory factory = (StdSchedulerFactory) servletContext
                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
		 // computer a time that is on the next round minute
	    Date runTime = TriggerUtils.getEvenMinuteDate(new Date());
	    scheduler = stdSchedulerFactory.getScheduler();
		scheduler.getSchedulerName();
		 // define the job and tie it to our HelloJob class
	    JobDetail job = new JobDetail("job1", "group1",SchedulerJob.class);
	    	//	newJob(SchedulerJob.class).withIdentity("job1", "group1").build();

	    // Trigger the job to run on the next round minute
	    System.out.println("Time before called " + Calendar.getInstance().getTime());
	    System.out.println(runTime);
	    SimpleTrigger trigger = new SimpleTrigger("trigger1", "group1",runTime);
	   // Trigger trigger = new 
	    		//newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();


	    // Tell quartz to schedule the job using our trigger
	    scheduler.scheduleJob(job, trigger);
	    

	    // Start up the scheduler (nothing can actually run until the
	    // scheduler has been started)
	    scheduler.start();

		}catch (Exception e) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			scheduler.shutdown();
		} catch (SchedulerException e1) {
			
			e1.printStackTrace();
		}
	}

	

	public List<QuartzJob> getQuartzJobList() {

		return quartzJobList;

	}

	public static class QuartzJob {

		private static final long serialVersionUID = 1L;

		String jobName;
		String jobGroup;
		Date nextFireTime;

		public QuartzJob(String jobName, String jobGroup, Date nextFireTime) {

			this.jobName = jobName;
			this.jobGroup = jobGroup;
			this.nextFireTime = nextFireTime;
		}

		public String getJobName() {
			return jobName;
		}

		public void setJobName(String jobName) {
			this.jobName = jobName;
		}

		public String getJobGroup() {
			return jobGroup;
		}

		public void setJobGroup(String jobGroup) {
			this.jobGroup = jobGroup;
		}

		public Date getNextFireTime() {
			return nextFireTime;
		}

		public void setNextFireTime(Date nextFireTime) {
			this.nextFireTime = nextFireTime;
		}

	}

}