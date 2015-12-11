package io.dmalone.personalfinancier.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataConversionController {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@RequestMapping("/convert")
	public @ResponseBody String handle() throws Exception{
		JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
		return "Executing conversion job";
	}
	
}
