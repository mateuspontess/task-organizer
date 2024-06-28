package br.com.mateus.taskorganizer.tests_utils;

import java.time.LocalDate;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;

public class TaskUtils {
    
    private static Random random = new Random();
    

    public static Task getRandomDefaultTaskWithoutId() {
        return getRandomTaskWithouId(null, null, null, null, null);
    }
    public static Task getRandomTaskWithouId(String title, String description, LocalDate dueDate, StatusTask status, Long userId) {
        title = paramOrDefaultString(title);
        description = paramOrDefaultString(description);
        dueDate = paramOrDefaultDate(dueDate);
        status = paramOrDefaultStatus(status);
        userId = paramOrDefaultLong(userId);

        return createTask(null, title, description, dueDate, status, userId);
    }

    private static LocalDate paramOrDefaultDate(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }
    private static String paramOrDefaultString(String string) {
        return string == null || string.isBlank() ? getRandomString() : string;
    }
    private static StatusTask paramOrDefaultStatus(StatusTask status) {
        return status == null ? getRandomStatus() : status;
    }
    private static Long paramOrDefaultLong(Long number) {
        return number == null ? 1L : number;
    }

	private static Task createTask(Long taskId, String title, String description, LocalDate dueDate, StatusTask status, Long userId) {
        return new TaskBuilder()
            .id(taskId)
            .title(title)
            .description(description)
            .dueDate(dueDate)
            .status(StatusTask.PENDING)
            .userId(1L)
            .build();
    }

    private static int getRandomInt() {
        return random.nextInt(100);
    }
    public static String getRandomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }
    public static StatusTask getRandomStatus() {
        StatusTask[] constants = StatusTask.values();
        return constants[random.nextInt(constants.length)];
    }
}