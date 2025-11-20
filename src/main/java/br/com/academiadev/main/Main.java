package br.com.academiadev.main;

import br.com.academiadev.infrastructure.persistence.*;
import br.com.academiadev.infrastructure.ui.*;
import br.com.academiadev.application.usecases.admins.*;
import br.com.academiadev.application.usecases.courses.*;
import br.com.academiadev.application.usecases.students.*;

public class Main {
    public static void main(String[] args) {
        
        var courseRepo = new CourseRepositoryInMemory();
        var studentRepo = new StudentRepositoryInMemory();
        var adminRepo = new AdminRepositoryInMemory();


        var createCourseUseCase = new CreateCourseUseCase(courseRepo);
        var listCoursesUseCase = new ListCoursesUseCase(courseRepo);
        var searchCourseUseCase = new SearchCourseUseCase(courseRepo);
        var updateCourseUseCase = new UpdateCourseUseCase(courseRepo);
        var inactivateCourseUseCase = new InactivateCourseUseCase(courseRepo);
        var activateCourseUseCase = new ActivateCourseUseCase(courseRepo);

        var registerStudentUseCase = new RegisterStudentUseCase(studentRepo);
        var listStudentsUseCase = new ListStudentsUseCase(studentRepo);
        var changeStudentPlanUseCase = new ChangeStudentPlanUseCase(studentRepo);
        var searchStudentUseCase = new SearchStudentUseCase(studentRepo);

        var registerAdminUseCase = new RegisterAdminUseCase(adminRepo);
        var listAdminsUseCase = new ListAdminsUseCase(adminRepo);


        var initialData = new InitialData(createCourseUseCase, inactivateCourseUseCase, registerStudentUseCase, registerAdminUseCase);
        initialData.populate();


        ConsoleUtils.printHeader("Welcome to AcademiaDev!");


        var courseMenu = new CourseMenu(
            createCourseUseCase, 
            listCoursesUseCase, 
            searchCourseUseCase, 
            updateCourseUseCase, 
            inactivateCourseUseCase, 
            activateCourseUseCase
        );

        var userManagementMenu = new UserManagementMenu(
            listStudentsUseCase, 
            changeStudentPlanUseCase, 
            searchStudentUseCase, 
            registerAdminUseCase,
            listAdminsUseCase);

        var reportsMenu = new ReportsMenu();

        var registerMenu = new RegisterMenu(registerStudentUseCase);

        var adminMenu = new AdminMenu(courseMenu, userManagementMenu, reportsMenu);

        var mainMenu = new MainMenu(adminMenu, registerMenu);



        mainMenu.show();
    }
}