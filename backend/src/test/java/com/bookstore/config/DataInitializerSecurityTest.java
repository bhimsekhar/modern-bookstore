```java
@Test
public void testDefaultUserCreation() {
    // Arrange
    DataInitializer dataInitializer = new DataInitializer(roleRepository, userRepository, passwordEncoder);

    // Act
    dataInitializer.run();

    // Assert
    assertNotNull(userRepository.findByUsername("admin"));
    assertNotNull(userRepository.findByUsername("employee1"));
}

@Test
public void testLogDoesNotContainPassword() {
    // Arrange
    DataInitializer dataInitializer = new DataInitializer(roleRepository, userRepository, passwordEncoder);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Act
    dataInitializer.run();

    // Assert
    assertFalse(outContent.toString().contains("Admin123"));
    assertFalse(outContent.toString().contains("Employee123"));
}