package org.example.tutorial.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.tutorial.repository.TutorialRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowData  implements Job {

    private final TutorialRepository tutorialRepository;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(tutorialRepository.findAll());
    }
}
