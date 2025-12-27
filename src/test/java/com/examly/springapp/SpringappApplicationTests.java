package com.examly.springapp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringappApplicationTests {


@Autowired
    private MockMvc mockMvc;


@Test
@Order(1)
    void Day3_test_Controller_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/controller").isDirectory());
    }

    @Test
	@Order(2)

    void Day3_test_Model_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/model").isDirectory());
    }

    @Test
	@Order(3)
    void Day3_test_Service_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/service").isDirectory());
    }

    @Test
	@Order(4)
    void Day3_test_Repository_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/repository").isDirectory());
    }  

        // DAY 4 TESTCASES - HOTEL APP

        @Test
        @Order(5)
        void Day4_test_RoomCategoryModel_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/model/RoomCategory.java").isFile());
        }

        @Test
        @Order(6)
        void Day4_test_RoomModel_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/model/Room.java").isFile());
        }

        @Test
        @Order(7)
        void Day4_test_BookingModel_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/model/Booking.java").isFile());
        }

        @Test
        @Order(8)
        void Day4_test_GuestModel_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/model/Guest.java").isFile());
        }

        @Test
        @Order(9)
        void Day4_test_PaymentModel_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/model/Payment.java").isFile());
        }

        @Test
        @Order(10)
        void Day4_test_Room_Has_Entity_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.model.Room");
                Class<?> annotation = Class.forName("jakarta.persistence.Entity");
        
                assertTrue(clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                        "@Entity annotation is missing on Room class");
        
            } catch (ClassNotFoundException e) {
                fail("Room class not found.");
            } catch (Exception e) {
                fail("Unable to check @Entity annotation on Room.");
            }
        }
        

        @Test
        @Order(11)
        void Day4_test_Room_Has_Id_Annotation_On_Field() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.model.Room");
                Class<?> idAnnotation = Class.forName("jakarta.persistence.Id");
        
                boolean found = false;
        
                for (var field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent((Class<? extends Annotation>) idAnnotation)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No field in Room class is annotated with @Id");
        
            } catch (ClassNotFoundException e) {
                fail("Room class not found.");
            } catch (Exception e) {
                fail("Unable to verify @Id annotation in Room class.");
            }
        }
        
	    //Day 5 Tetscases
        @Test
        @Order(12)
        void Day5_testRoomCategoryRepo_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/repository/RoomCategoryRepo.java").isFile());
        }

        @Test
        @Order(13)
        void Day5_testRoomRepo_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/repository/RoomRepo.java").isFile());
        }

        @Test
        @Order(14)
        void Day5_testGuestRepo_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/repository/GuestRepo.java").isFile());
        }

        @Test
        @Order(15)
        void Day5_testBookingRepo_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/repository/BookingRepo.java").isFile());
        }

        @Test
        @Order(16)
        void Day5_testPaymentRepo_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/repository/PaymentRepo.java").isFile());
        }


        @Test
        @Order(17)
        void Day5_test_RoomRepo_Has_Repository_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.repository.RoomRepo");
                Class<?> annotation = Class.forName("org.springframework.stereotype.Repository");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@Repository annotation is missing on RoomRepo class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("RoomRepo class not found.");
            } catch (Exception e) {
                fail("Unable to verify @Repository annotation on RoomRepo.");
            }
        }
        

        @Test
        @Order(18)
        void Day5_test_RoomCategoryRepo_Has_Repository_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.repository.RoomCategoryRepo");
                Class<?> annotation = Class.forName("org.springframework.stereotype.Repository");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@Repository annotation is missing on RoomCategoryRepo class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryRepo class not found.");
            } catch (Exception e) {
                fail("Unable to verify @Repository annotation on RoomCategoryRepo.");
            }
        }
        

        @Test
        @Order(19)
        void Day5_test_GuestRepo_Has_Repository_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.repository.GuestRepo");
                Class<?> annotation = Class.forName("org.springframework.stereotype.Repository");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@Repository annotation is missing on GuestRepo class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("GuestRepo class not found.");
            } catch (Exception e) {
                fail("Unable to verify @Repository annotation on GuestRepo.");
            }
        }
        

        @Test
        @Order(20)
        void Day5_test_BookingRepo_Has_Repository_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.repository.BookingRepo");
                Class<?> annotation = Class.forName("org.springframework.stereotype.Repository");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@Repository annotation is missing on BookingRepo class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("BookingRepo class not found.");
            } catch (Exception e) {
                fail("Unable to verify @Repository annotation on BookingRepo.");
            }
        }
        


        @Test
        @Order(21)
        void Day5_test_PaymentRepo_Has_Repository_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.repository.PaymentRepo");
                Class<?> annotation = Class.forName("org.springframework.stereotype.Repository");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@Repository annotation is missing on PaymentRepo class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("PaymentRepo class not found.");
            } catch (Exception e) {
                fail("Unable to verify @Repository annotation on PaymentRepo.");
            }
        }
        

        // DAY 6 — Controller existence test cases
        @Test
        @Order(22)
        void Day6_test_RoomCategoryController_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/controller/RoomCategoryController.java").isFile());
        }

        @Test
        @Order(23)
        void Day6_test_RoomController_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/controller/RoomController.java").isFile());
        }

        @Test
        @Order(24)
        void Day6_test_GuestController_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/controller/GuestController.java").isFile());
        }

        @Test
        @Order(25)
        void Day6_test_BookingController_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/controller/BookingController.java").isFile());
        }

        @Test
        @Order(26)
        void Day6_test_PaymentController_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/controller/PaymentController.java").isFile());
        }



        @Test
        @Order(27)
        void Day6_test_RoomCategoryController_Has_RestController_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> annotation = Class.forName("org.springframework.web.bind.annotation.RestController");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@RestController annotation is missing on RoomCategoryController class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @RestController annotation on RoomCategoryController.");
            }
        }
        
	
        @Test
        @Order(28)
        void Day6_test_RoomController_Has_RestController_Annotation() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomController");
                Class<?> annotation = Class.forName("org.springframework.web.bind.annotation.RestController");
        
                assertTrue(
                    clazz.isAnnotationPresent((Class<? extends Annotation>) annotation),
                    "@RestController annotation is missing on RoomController class"
                );
        
            } catch (ClassNotFoundException e) {
                fail("RoomController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @RestController annotation on RoomController.");
            }
        }
        

        @Test
        @Order(29)
        void Day6_test_RoomCategoryController_Has_PostMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> postMapping = Class.forName("org.springframework.web.bind.annotation.PostMapping");
        
                boolean found = false;
        
                for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent((Class<? extends Annotation>) postMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No method with @PostMapping found in RoomCategoryController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @PostMapping annotation in RoomCategoryController.");
            }
        }
        
        @Test
        @Order(30)
        void Day6_test_RoomCategoryController_Has_GetMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> getMapping = Class.forName("org.springframework.web.bind.annotation.GetMapping");
        
                boolean found = false;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent((Class<? extends Annotation>) getMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No @GetMapping method found in RoomCategoryController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @GetMapping in RoomCategoryController.");
            }
        }
        

        @Test
        @Order(31)
        void Day6_test_RoomCategoryController_Has_PutMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> putMapping = Class.forName("org.springframework.web.bind.annotation.PutMapping");
        
                boolean found = false;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent((Class<? extends Annotation>) putMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No @PutMapping method found in RoomCategoryController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @PutMapping in RoomCategoryController.");
            }
        }
        

        @Test
        @Order(32)
        void Day6_test_RoomCategoryController_Has_DeleteMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> deleteMapping = Class.forName("org.springframework.web.bind.annotation.DeleteMapping");
        
                boolean found = false;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent((Class<? extends Annotation>) deleteMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No @DeleteMapping method found in RoomCategoryController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @DeleteMapping in RoomCategoryController.");
            }
        }
        
        @Test
        @Order(33)
        public void Day6_testCreateCategory_NoBody_StatusBadRequest() throws Exception {
        
            mockMvc.perform(MockMvcRequestBuilders.post("/api/room-categories")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
        
        @Test
        @Order(34)
        public void Day6_testGetAllCategories_StatusNoContent() throws Exception {
        
            mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }
        

        @Test
        @Order(35)
        public void Day6_testGetCategoryById_StatusNotFound() throws Exception {
        
            mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/999"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content()
                            .string(org.hamcrest.Matchers.containsString("Room category not found")));
        }
        
        
        @Test
        @Order(36)
        void Day7_test_RoomCategoryController_Has_RequestMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> requestMapping = Class.forName("org.springframework.web.bind.annotation.RequestMapping");
        
                boolean found = false;
        
                // Class-level check
                if (clazz.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                    found = true;
                }
        
                // Method-level check
                for (Method m : clazz.getDeclaredMethods()) {
                    if (m.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No @RequestMapping found on RoomCategoryController (class or methods)");
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @RequestMapping in RoomCategoryController.");
            }
        }
        
        @Test
        @Order(37)
        void Day7_test_RoomCategoryController_Has_PathVariable() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomCategoryController");
                Class<?> pathVariable = Class.forName("org.springframework.web.bind.annotation.PathVariable");
        
                boolean found = false;
        
                for (Method method : clazz.getDeclaredMethods()) {
                    for (Parameter param : method.getParameters()) {
                        if (param.isAnnotationPresent((Class<? extends Annotation>) pathVariable)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
        
                assertTrue(found, "No @PathVariable found in RoomCategoryController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomCategoryController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @PathVariable in RoomCategoryController.");
            }
        }
        

        @Test
        @Order(38)
        void Day7_test_RoomController_Has_RequestMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomController");
                Class<?> requestMapping = Class.forName("org.springframework.web.bind.annotation.RequestMapping");
        
                boolean found = false;
        
                // Class-level check
                if (clazz.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                    found = true;
                }
        
                // Method-level check
                for (Method m : clazz.getDeclaredMethods()) {
                    if (m.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No @RequestMapping found on RoomController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @RequestMapping in RoomController.");
            }
        }
        

        @Test
        @Order(39)
        void Day7_test_BookingController_Has_RequestMapping() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.BookingController");
                Class<?> requestMapping = Class.forName("org.springframework.web.bind.annotation.RequestMapping");
        
                boolean found = false;
        
                // Class-level check
                if (clazz.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                    found = true;
                }
        
                // Method-level check
                for (Method m : clazz.getDeclaredMethods()) {
                    if (m.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                        found = true;
                        break;
                    }
                }
        
                assertTrue(found, "No @RequestMapping found on BookingController");
        
            } catch (ClassNotFoundException e) {
                fail("BookingController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @RequestMapping in BookingController.");
            }
        }
        

        @Test
        @Order(40)
        void Day7_test_RoomController_Has_PathVariable() {
            try {
                Class<?> clazz = Class.forName("com.examly.springapp.controller.RoomController");
                Class<?> pathVariable = Class.forName("org.springframework.web.bind.annotation.PathVariable");
        
                boolean found = false;
        
                for (Method method : clazz.getDeclaredMethods()) {
                    for (Parameter param : method.getParameters()) {
                        if (param.isAnnotationPresent((Class<? extends Annotation>) pathVariable)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
        
                assertTrue(found, "No @PathVariable found in RoomController");
        
            } catch (ClassNotFoundException e) {
                fail("RoomController class not found.");
            } catch (Exception e) {
                fail("Unable to verify @PathVariable in RoomController.");
            }
        }
        


    @Test
    @Order(41)
    void Day7_test_BookingController_Has_PathVariable() {
        try {
            Class<?> clazz = Class.forName("com.examly.springapp.controller.BookingController");
            Class<?> pathVariable = Class.forName("org.springframework.web.bind.annotation.PathVariable");
    
            boolean found = false;
    
            for (Method method : clazz.getDeclaredMethods()) {
                for (Parameter param : method.getParameters()) {
                    if (param.isAnnotationPresent((Class<? extends Annotation>) pathVariable)) {
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
    
            assertTrue(found, "No @PathVariable found in BookingController");
    
        } catch (ClassNotFoundException e) {
            fail("BookingController class not found.");
        } catch (Exception e) {
            fail("Unable to verify @PathVariable in BookingController.");
        }
    }
    


    @Test
    @Order(42)
    void Day7_test_PaymentController_Has_RequestMapping() {
        try {
            Class<?> clazz = Class.forName("com.examly.springapp.controller.PaymentController");
            Class<?> requestMapping = Class.forName("org.springframework.web.bind.annotation.RequestMapping");
    
            boolean found = false;
    
            // Class-level annotation check
            if (clazz.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                found = true;
            }
    
            // Method-level annotation check
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent((Class<? extends Annotation>) requestMapping)) {
                    found = true;
                    break;
                }
            }
    
            assertTrue(found, "No @RequestMapping found on PaymentController");
    
        } catch (ClassNotFoundException e) {
            fail("PaymentController class not found.");
        } catch (Exception e) {
            fail("Unable to verify @RequestMapping in PaymentController.");
        }
    }
    

        @Test
        @Order(43)
        void Day8_test_RoomCategoryService_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/service/RoomCategoryService.java").isFile());
        }
        
        @Test
        @Order(44)
        void Day8_test_RoomService_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/service/RoomService.java").isFile());
        }
        
        @Test
        @Order(45)
        void Day8_test_GuestService_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/service/GuestService.java").isFile());
        }
        
        @Test
        @Order(46)
        void Day8_test_BookingService_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/service/BookingService.java").isFile());
        }
        
        @Test
        @Order(47)
        void Day8_test_PaymentService_File_Exists() {
            assertTrue(new File("src/main/java/com/examly/springapp/service/PaymentService.java").isFile());
        }
        
            @Test
            @Order(48)
            void Day8_test_RoomCategoryServiceImpl_File_Exists() {
                assertTrue(new File("src/main/java/com/examly/springapp/service/RoomCategoryServiceImpl.java").isFile());
            }

            @Test
            @Order(49)
            void Day8_test_RoomServiceImpl_File_Exists() {
                assertTrue(new File("src/main/java/com/examly/springapp/service/RoomServiceImpl.java").isFile());
            }

            @Test
            @Order(50)
            void Day8_test_GuestServiceImpl_File_Exists() {
                assertTrue(new File("src/main/java/com/examly/springapp/service/GuestServiceImpl.java").isFile());
            }

            @Test
            @Order(51)
            void Day8_test_BookingServiceImpl_File_Exists() {
                assertTrue(new File("src/main/java/com/examly/springapp/service/BookingServiceImpl.java").isFile());
            }

            @Test
            @Order(52)
            void Day8_test_PaymentServiceImpl_File_Exists() {
                assertTrue(new File("src/main/java/com/examly/springapp/service/PaymentServiceImpl.java").isFile());
            }


            @Test
            @Order(53)
            public void Day8_testAddRoomCategory() throws Exception {
                String requestBody = "{ \"categoryName\": \"Deluxe\" }";
            
                mockMvc.perform(MockMvcRequestBuilders.post("/api/room-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Deluxe"))
                        .andReturn();
            }
            
            @Test
            @Order(54)
            public void Day8_testGetAllRoomCategories() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName").value("Deluxe"))
                        .andReturn();
            }
            
            

            @Test
            @Order(55)
            public void Day8_testGetRoomCategoryById() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Deluxe"))
                        .andReturn();
            }
            
    // PUT /api/categories/{id}
            @Test
            @Order(56)
            public void Day8_testUpdateRoomCategory() throws Exception {
                String requestBody = "{ \"categoryName\": \"Premium\" }";
            
                mockMvc.perform(MockMvcRequestBuilders.put("/api/room-categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Premium"))
                        .andReturn();
            }
            

            @Test
            @Order(57)
            public void Day9_testPagination_PageNumberApplied() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/page/0/5")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(0));
            }
            

            @Test
            @Order(58)
            public void Day9_testPagination_PageSizeApplied() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/page/1/10")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(10));
            }
            

            @Test
            @Order(59)
            public void Day9_testPagination_SortingPresent() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/page/0/5")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.sort").exists())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.sort.sorted").isBoolean());
            }
            

            @Test
            @Order(60)
            public void Day9_testPagination_ContentArrayExists() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/page/0/5")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
            }
            

            @Test
            @Order(61)
            public void Day9_testPagination_TotalElementsExists() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/page/0/5")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").exists());
            }
            

            @Test
            @Order(62)
            public void Day9_testPagination_TotalPagesExists() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/room-categories/page/0/5")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").exists());
            }
            


    
            @Test
            @Order(63)
            public void Day10_testAddRoom() throws Exception {
                String requestBody = "{ \"roomNumber\": \"101\", \"pricePerNight\": 2500, \"available\": true, \"roomCategory\": { \"categoryId\": 1 } }";
            
                mockMvc.perform(MockMvcRequestBuilders.post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.roomNumber").value("101"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerNight").value(2500))
                        .andReturn();
            }
            
            @Test
            @Order(64)
            public void Day10_testGetAllRooms() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].roomNumber").value("101"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].pricePerNight").value(2500))
                        .andReturn();
            }
            

            @Test
            @Order(65)
            public void Day10_testGetRoomById() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.roomNumber").value("101"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerNight").value(2500))
                        .andReturn();
            }
            
            @Test
            @Order(66)
            public void Day10_testUpdateRoom() throws Exception {
                String requestBody = "{ \"roomNumber\": \"102\", \"pricePerNight\": 3000, \"available\": false, \"roomCategory\": { \"categoryId\": 1 } }";
            
                mockMvc.perform(MockMvcRequestBuilders.put("/api/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.roomNumber").value("102"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerNight").value(3000))
                        .andReturn();
            }
            

            @Test
            @Order(67)
            public void Day10_testAddGuest() throws Exception {
                String requestBody = "{ \"name\": \"Ravi Kumar\", \"phone\": \"9876543210\", \"email\": \"ravi@gmail.com\" }";
            
                mockMvc.perform(MockMvcRequestBuilders.post("/api/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value("Ravi Kumar"))
                        .andExpect(jsonPath("$.phone").value("9876543210"))
                        .andExpect(jsonPath("$.email").value("ravi@gmail.com"))
                        .andReturn();
            }
            
            @Test
            @Order(68)
            public void Day10_testGetAllGuests() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/guests")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].name").value("Ravi Kumar"))
                        .andReturn();
            }
            
            @Test
            @Order(69)
            public void Day10_testGetGuestById() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.guestId").value(1))
                        .andReturn();
            }
            

            @Test
            @Order(70)
            public void Day10_testUpdateGuest() throws Exception {
                String requestBody = "{ \"name\": \"Ravi Sharma\", \"phone\": \"9123456789\", \"email\": \"ravi@hotel.com\" }";
            
                mockMvc.perform(MockMvcRequestBuilders.put("/api/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("Ravi Sharma"))
                        .andExpect(jsonPath("$.phone").value("9123456789"))
                        .andExpect(jsonPath("$.email").value("ravi@hotel.com"))
                        .andReturn();
            }
            

            @Test
            @Order(71)
            public void Day11_testGetGuestsByPhone() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/phone/9123456789")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].name").value("Ravi Sharma"))
                        .andExpect(jsonPath("$[0].phone").value("9123456789"))
                        .andReturn();
            }
            

            @Test
            @Order(72)
            public void Day12_testGetRoomsByCategoryName() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/category/Premium")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].roomNumber").value("102"))
                        .andExpect(jsonPath("$[0].pricePerNight").value(3000))
                        .andReturn();
            }
            


            @Test
            @Order(73)
            public void Day12_testGetGuestByEmail() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/email/ravi@hotel.com")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].email").value("ravi@hotel.com"))
                        .andReturn();
            }
            
            @Test
            @Order(74)
            public void Day12_testGetGuestByPhone_NotFound() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/phone/9999999999")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent())
                        .andExpect(content().string("No guest found with phone: 9999999999"));
            }
            


            @Test
            @Order(75)
            public void Day12_testGetRoomByNumber() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/number/102")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].roomNumber").value("102"))
                        .andReturn();
            }
            
            @Test
            @Order(76)
            public void Day12_testGetRoomByNumber_NotFound() throws Exception {
            
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/number/999")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andExpect(MockMvcResultMatchers.content().string("No room found with number: 999"));
            }
            

@Test
@Order(77)
    void Day13_test_execption_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/exception").isDirectory());
    }


	
@Test
@Order(78)
void Day13_test_GloabalEception_File_Exists() {
    assertTrue(new File("src/main/java/com/examly/springapp/exception/GlobalExceptionHandler.java").isFile());
}


@Test
@Order(79)
    void Day14_test_configuartion_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/configuration").isDirectory());
    }

@Test
@Order(80)
public void Day15_testAOPLogFileExists() {

    assertTrue(new File("src/main/java/com/examly/springapp/aop").isDirectory());
   
}
}


















