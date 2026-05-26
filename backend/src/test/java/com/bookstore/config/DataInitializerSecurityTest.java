```java
@Test
public void testDefaultAdminUserCreated() {
    // Set environment variables
    System.setProperty("ADMIN_PASSWORD", "TestAdmin123");
    System.setProperty("EMPLOYEE_PASSWORD", "TestEmployee123");

    // Run DataInitializer
    DataInitializer initializer = new DataInitializer(roleRepository, userRepository, passwordEncoder);
    initializer.run();

    // Verify default admin user created
    assertTrue(userRepository.existsByUsername("admin"));
}

@Test
public void testDefaultEmployeeUserCreated() {
    // Set environment variables
    System.setProperty("ADMIN_PASSWORD", "TestAdmin123");
    System.setProperty("EMPLOYEE_PASSWORD", "TestEmployee123");

    // Run DataInitializer
    DataInitializer initializer = new DataInitializer(roleRepository, userRepository, passwordEncoder);
    initializer.run();

    // Verify default employee user created
    assertTrue(userRepository.existsByUsername("employee1"));
}

@Test(expected = RuntimeException.class)
public void testEnvironmentVariablesNotSet() {
    // Do not set environment variables

    // Run DataInitializer
    DataInitializer initializer = new DataInitializer(roleRepository, userRepository, passwordEncoder);
    initializer.run();
}
```