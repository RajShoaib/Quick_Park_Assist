// package com.quick_park_assist.controllerTest;



// import com.quick_park_assist.controller.ModifySpotController;
// import com.quick_park_assist.entity.BookingSpot;
// import com.quick_park_assist.entity.ParkingSpot;
// import com.quick_park_assist.entity.User;
// import com.quick_park_assist.service.IModifySpotService;
// import jakarta.servlet.http.HttpSession;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.ui.Model;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;

// class ModifyBookingControllerTest {

//     @Mock
//     private IModifySpotService modifySpotService;

//     @Mock
//     private HttpSession session;

//     @Mock
//     private Model model;

//     @Mock
//     private RedirectAttributes redirectAttributes;

//     @InjectMocks
//     private ModifySpotController modifySpotController;

//     @BeforeEach
//     public void setUpAdd() {
//         MockitoAnnotations.openMocks(this);  // Initialize mocks
//        long loggedInUserId = 1L;  // Sample logged-in user ID

//         // Creating a mock user
//         User user = new User();
//         user.setId(loggedInUserId);

//         // Creating a mock parking spot
//         ParkingSpot parkingSpot = new ParkingSpot();
//         parkingSpot.setSpotLocation("Location1");
//         parkingSpot.setLocation("Address1");
//     }

//     // Test cases go here
//     @Test
//     void testFetchConfirmedBookings_UserNotInSession() {
//         // Arrange
//         when(session.getAttribute("userId")).thenReturn(null);

//         // Act
//         String viewName = modifySpotController.fetchConfirmedBookings(session, model);

//         // Assert
//         assertEquals("redirect:/login", viewName);
//         verifyNoInteractions(model);
//     }
//     //second Test-case
//     @Test
//     void testFetchConfirmedBookings_UserInSession() {
//         // Arrange
//         Long userId = 1L;
//         when(session.getAttribute("userId")).thenReturn(userId);

//         List<BookingSpot> bookings = new ArrayList<>();
//         bookings.add(new BookingSpot()); // Add dummy booking
//         when(modifySpotService.getConfirmedBookings(userId)).thenReturn(bookings);

//         // Act
//         String viewName = modifySpotController.fetchConfirmedBookings(session, model);

//         // Assert
//         assertEquals("ModifyBooking", viewName);
//         verify(model).addAttribute("bookings", bookings);
//     }
//     //third -Testcase
//     @Test
//     void testHandleGetRequest() {
//         // Act
//         String viewName = modifySpotController.handleGetRequest();

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//     }
//     //forth -testCase
//     @Test
//     void testUpdateSpotDetails_ValidData() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "2026-01-25T10:00";
//         Double duration = 2.0;

//         Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
//         when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotID)).thenReturn(true);

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("successMessage", "Booking updated successfully!");
//     }
//     //fifth-TestCase
//     @Test
//     void testUpdateSpotDetails_InvalidStartTime() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 101L;
//         String startTimeStr = "2023-01-01T10:00"; // Past date
//         Double duration = 2.0;

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//         verifyNoInteractions(modifySpotService);
//     }
//     //sixth-TestCase
//     @Test
//     void testUpdateSpotDetails_InvalidDuration() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 101L;
//         String startTimeStr = "3030-01-25T10:00";
//         Double duration = -1.0; // Invalid duration

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a valid duration.");
//         verifyNoInteractions(modifySpotService);
//     }
//     //seventh-TestCase
//     @Test
//     void testUpdateSpotDetails_InvalidDateFormat() {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 101L;
//         String startTimeStr = "invalid-date";
//         Double duration = 2.0;

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Invalid date format. Please use the correct format.Try Again");
//         verifyNoInteractions(modifySpotService);
//     }
//     @Test
//     void testUpdateSpotDetails_UpdateFailed() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "2026-01-25T10:00";
//         Double duration = 2.0;

//         Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
//         when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotID)).thenReturn(false);

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Update failed. Booking ID not found.");
//     }
//     @Test
//     void testFetchConfirmedBookings_NoBookings() {
//         // Arrange
//         Long userId = 1L;
//         when(session.getAttribute("userId")).thenReturn(userId);
//         when(modifySpotService.getConfirmedBookings(userId)).thenReturn(new ArrayList<>());

//         // Act
//         String viewName = modifySpotController.fetchConfirmedBookings(session, model);

//         // Assert
//         assertEquals("ModifyBooking", viewName);
//         verify(model).addAttribute("bookings", new ArrayList<>());
//     }

//     @Test
//     void testUpdateSpotDetails_DurationExceedsLimit() {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "2025-01-25T10:00";
//         Double duration = 25.0; // Exceeds maximum allowed duration

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//         verifyNoInteractions(modifySpotService);
//     }

//     @Test
//     void testUpdateSpotDetails_NullDuration() {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "3030-01-25T10:00";

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, null, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a valid duration.");
//         verifyNoInteractions(modifySpotService);
//     }

//     @Test
//     void testUpdateSpotDetails_ServiceThrowsException() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "2025-01-25T10:00";
//         Double duration = 2.0;

//         Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
//         when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotID)).thenThrow(new RuntimeException("Service error"));

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//     }


//     // Test for null spot ID
//     @Test
//     void testUpdateSpotDetails_NullSpotId() {
//         // Arrange
//         Long bookingId = 1L;
//         String startTimeStr = "2025-01-25T10:00";
//         Double duration = 2.0;

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, null, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//         verifyNoInteractions(modifySpotService);
//     }

//     // Test for service returning null
//     @Test
//     void testUpdateSpotDetails_ServiceReturnsNull() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "2025-02-25T10:00";
//         Double duration = 2.0;

//         Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
//         when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotID)).thenReturn(false);

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Update failed. Booking ID not found.");
//     }
//     @Test
//     void testFetchConfirmedBookings_NullMobileNumber() {
//         // Arrange
//         Long userId = 1L;
//         when(session.getAttribute("userId")).thenReturn(userId);
//         when(modifySpotService.getConfirmedBookings(userId)).thenReturn(new ArrayList<>());

//         // Act
//         String viewName = modifySpotController.fetchConfirmedBookings(session, model);

//         // Assert
//         assertEquals("ModifyBooking", viewName);
//         verify(model).addAttribute("bookings", new ArrayList<>());
//         verify(model).addAttribute("mobileNumber", null);
//     }
//     @Test
//     void testUpdateSpotDetails_NullBookingId() {
//         // Arrange
//         Long spotID = 1L;
//         String startTimeStr = "2025-01-25T10:00";
//         Double duration = 2.0;

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(null, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Update failed. Booking ID not found.");
//         verifyNoInteractions(modifySpotService);
//     }
//     @Test
//     void testUpdateSpotDetails_InvalidSpotId() {
//         // Arrange
//         Long bookingId = 1L;
//         String startTimeStr = "2025-01-25T10:00";
//         Double duration = 2.0;

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, -1L, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//         verifyNoInteractions(modifySpotService);
//     }
//     @Test
//     void testFetchConfirmedBookings_EmptyBookings() {
//         // Arrange
//         Long userId = 1L;
//         when(session.getAttribute("userId")).thenReturn(userId);
//         when(modifySpotService.getConfirmedBookings(userId)).thenReturn(new ArrayList<>());

//         // Act
//         String viewName = modifySpotController.fetchConfirmedBookings(session, model);

//         // Assert
//         assertEquals("ModifyBooking", viewName);
//         verify(model).addAttribute("bookings", new ArrayList<>());
//     }

//     @Test
//     void testUpdateSpotDetails_StartTimeAtCurrentTime() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         Double duration = 2.0;

//         String startTimeStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date()); // Current time

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//         verifyNoInteractions(modifySpotService);
//     }



//     @Test
//     void testUpdateSpotDetails_ServiceExceptionDuringUpdate() throws Exception {
//         // Arrange
//         Long bookingId = 1L;
//         Long spotID = 1L;
//         String startTimeStr = "2025-01-25T10:00";
//         Double duration = 2.0;

//         Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
//         when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotID))
//                 .thenThrow(new RuntimeException("Service failure"));

//         // Act
//         String viewName = modifySpotController.updateSpotDetails(bookingId, spotID, startTimeStr, duration, redirectAttributes);

//         // Assert
//         assertEquals("redirect:/modifySpot/", viewName);
//         verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
//     }

// }

package com.quick_park_assist.controllerTest;

import com.quick_park_assist.controller.ModifySpotController;
import com.quick_park_assist.entity.BookingSpot;
import com.quick_park_assist.service.IModifySpotService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ModifyBookingControllerTest {

    @Mock
    private IModifySpotService modifySpotService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ModifySpotController modifySpotController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- Fetch Confirmed Bookings Tests ---

    @Test
    void testFetchConfirmedBookings_UserNotInSession() {
        when(session.getAttribute("userId")).thenReturn(null);

        String viewName = modifySpotController.fetchConfirmedBookings(session, model);

        assertEquals("redirect:/login", viewName);
        verifyNoInteractions(model);
    }

    @Test
    void testFetchConfirmedBookings_UserInSession() {
        Long userId = 1L;
        when(session.getAttribute("userId")).thenReturn(userId);

        List<BookingSpot> bookings = new ArrayList<>();
        bookings.add(new BookingSpot());

        when(modifySpotService.getConfirmedBookings(userId)).thenReturn(bookings);

        String viewName = modifySpotController.fetchConfirmedBookings(session, model);

        assertEquals("ModifyBooking", viewName);
        verify(model).addAttribute("bookings", bookings);
    }

    @Test
    void testFetchConfirmedBookings_NoBookings() {
        Long userId = 1L;
        when(session.getAttribute("userId")).thenReturn(userId);
        when(modifySpotService.getConfirmedBookings(userId)).thenReturn(new ArrayList<>());

        String viewName = modifySpotController.fetchConfirmedBookings(session, model);

        assertEquals("ModifyBooking", viewName);
        verify(model).addAttribute("bookings", new ArrayList<>());
    }

    @Test
    void testFetchConfirmedBookings_EmptyBookings() {
        testFetchConfirmedBookings_NoBookings(); // Same functionality
    }

    @Test
    void testFetchConfirmedBookings_NullMobileNumber() {
        Long userId = 1L;
        when(session.getAttribute("userId")).thenReturn(userId);
        when(modifySpotService.getConfirmedBookings(userId)).thenReturn(new ArrayList<>());

        String viewName = modifySpotController.fetchConfirmedBookings(session, model);

        assertEquals("ModifyBooking", viewName);
        verify(model).addAttribute("bookings", new ArrayList<>());
        verify(model).addAttribute("mobileNumber", null);
    }

    // --- GET Mapping Redirection Test ---
    @Test
    void testHandleGetRequest() {
        assertEquals("redirect:/modifySpot/", modifySpotController.handleGetRequest());
    }

    // --- Update Spot Details Tests ---

    @Test
    void testUpdateSpotDetails_ValidData() throws Exception {
        Long bookingId = 1L, spotId = 1L;
        String startTimeStr = "2026-01-25T10:00";
        Double duration = 2.0;

        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
        when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotId)).thenReturn(true);

        String view = modifySpotController.updateSpotDetails(bookingId, spotId, startTimeStr, duration, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("successMessage", "Booking updated successfully!");
    }

    @Test
    void testUpdateSpotDetails_UpdateFailed() throws Exception {
        Long bookingId = 1L, spotId = 1L;
        String startTimeStr = "2026-01-25T10:00";
        Double duration = 2.0;

        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
        when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotId)).thenReturn(false);

        String view = modifySpotController.updateSpotDetails(bookingId, spotId, startTimeStr, duration, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Update failed. Booking ID not found.");
    }

    @Test
    void testUpdateSpotDetails_InvalidStartTime() {
        String pastTimeStr = "2023-01-01T10:00";

        String view = modifySpotController.updateSpotDetails(1L, 101L, pastTimeStr, 2.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_InvalidDuration() {
        String futureTimeStr = "3030-01-25T10:00";

        String view = modifySpotController.updateSpotDetails(1L, 101L, futureTimeStr, -1.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a valid duration.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_DurationExceedsLimit() {
        String futureTimeStr = "2025-01-25T10:00";

        String view = modifySpotController.updateSpotDetails(1L, 1L, futureTimeStr, 25.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_NullDuration() {
        String futureTimeStr = "3030-01-25T10:00";

        String view = modifySpotController.updateSpotDetails(1L, 1L, futureTimeStr, null, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a valid duration.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_InvalidDateFormat() {
        String view = modifySpotController.updateSpotDetails(1L, 101L, "invalid-date", 2.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Invalid date format. Please use the correct format.Try Again");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_ServiceThrowsException() throws Exception {
        Long bookingId = 1L, spotId = 1L;
        String startTimeStr = "2025-01-25T10:00";
        Double duration = 2.0;

        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
        when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotId)).thenThrow(new RuntimeException("Service error"));

        String view = modifySpotController.updateSpotDetails(bookingId, spotId, startTimeStr, duration, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
    }

    @Test
    void testUpdateSpotDetails_NullSpotId() {
        String view = modifySpotController.updateSpotDetails(1L, null, "2025-01-25T10:00", 2.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_NullBookingId() {
        String view = modifySpotController.updateSpotDetails(null, 1L, "2025-01-25T10:00", 2.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Update failed. Booking ID not found.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_InvalidSpotId() {
        String view = modifySpotController.updateSpotDetails(1L, -1L, "2025-01-25T10:00", 2.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_StartTimeAtCurrentTime() {
        String currentTimeStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());

        String view = modifySpotController.updateSpotDetails(1L, 1L, currentTimeStr, 2.0, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
        verifyNoInteractions(modifySpotService);
    }

    @Test
    void testUpdateSpotDetails_ServiceExceptionDuringUpdate() throws Exception {
        Long bookingId = 1L, spotId = 1L;
        String startTimeStr = "2025-01-25T10:00";
        Double duration = 2.0;

        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTimeStr);
        when(modifySpotService.updateSpotDetails(bookingId, startTime, duration, spotId)).thenThrow(new RuntimeException("Service failure"));

        String view = modifySpotController.updateSpotDetails(bookingId, spotId, startTimeStr, duration, redirectAttributes);

        assertEquals("redirect:/modifySpot/", view);
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Please select a future date and time.");
    }
}
