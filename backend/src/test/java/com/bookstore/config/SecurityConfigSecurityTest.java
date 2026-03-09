```java
@Test
public void testActuatorEndpointAccess() {
    // Test with authorized user
    mockMvc.perform(get("/actuator/health")
            .with(jwtAuthFilter.withRole("ACTUATOR")))
            .andExpect(status().isOk());

    // Test with unauthorized user
    mockMvc.perform(get("/actuator/health")
            .with(jwtAuthFilter.withoutRole("ACTUATOR")))
            .andExpect(status().isForbidden());
}
```