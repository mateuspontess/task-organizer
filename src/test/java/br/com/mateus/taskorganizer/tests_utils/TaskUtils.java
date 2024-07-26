package br.com.mateus.taskorganizer.tests_utils;

import java.time.LocalDate;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;

public class TaskUtils {
    
    private static Random random = new Random();
    

    public static Task getRandomTask() {
        Task task = getTaskOrRandom(null, null, null, null, null, null);
        return task;
    }
    public static Task getRandomTaskWithoutId() {
        Task task = getTaskOrRandom(null, null, null, null, null, null);
        return task;
    }
    public static Task getRandomTaskWithoutId(String id, String title, String description, LocalDate dueDate, StatusTask status, String userId) {
        Task task = getTaskOrRandom(null, null, null, null, null, userId);
        ReflectionTestUtils.setField(task, "id", null);
        return task;
    }
    public static Task getTaskOrRandom(String id, String title, String description, LocalDate dueDate, StatusTask status, String userId) {
        id = paramOrDefaultString(id);
        title = paramOrDefaultString(title);
        description = paramOrDefaultString(description);
        dueDate = paramOrDefaultDate(dueDate);
        status = paramOrDefaultStatus(status);
        userId = paramOrDefaultString(userId);

        return createTask(id, title, description, dueDate, status, userId);
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

	private static Task createTask(String taskId, String title, String description, LocalDate dueDate, StatusTask status, String userId) {
        return new TaskBuilder()
            .id(taskId)
            .title(title)
            .description(description)
            .dueDate(dueDate)
            .status(StatusTask.PENDING)
            .userId(userId)
            .build();
    }

    // private static int getRandomInt() {
    //     return random.nextInt(100);
    // }
    public static String getRandomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }
    public static StatusTask getRandomStatus() {
        StatusTask[] constants = StatusTask.values();
        return constants[random.nextInt(constants.length)];
    }
}