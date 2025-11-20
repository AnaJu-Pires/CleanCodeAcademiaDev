package br.com.academiadev.main;

import br.com.academiadev.infrastructure.persistence.*;
import br.com.academiadev.infrastructure.ui.*;
import br.com.academiadev.application.usecases.courses.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nWelcome to AcademiaDev!");

        var courseRepo = new CourseRepositoryInMemory();


        var createCourseUseCase = new CreateCourseUseCase(courseRepo);
        var listCoursesUseCase = new ListCoursesUseCase(courseRepo);
        var searchCourseUseCase = new SearchCourseUseCase(courseRepo);
        var updateCourseUseCase = new UpdateCourseUseCase(courseRepo);

        var courseMenu = new CourseMenu(createCourseUseCase, listCoursesUseCase, searchCourseUseCase, updateCourseUseCase);
        var mainMenu = new MainMenu(courseMenu);

        mainMenu.show();
    }
}