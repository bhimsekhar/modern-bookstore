```java
@Test
public void testActuatorEndpointAccess() throws Exception {
    // Test that actuator endpoint is not accessible without authentication
    mockMvc.perform(get("/actuator/health"))
            .andExpect(status().isUnauthorized());

    // Test that actuator endpoint is accessible with authentication
    String token = obtainAccessToken();
    mockMvc.perform(get("/actuator/health")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
}
```