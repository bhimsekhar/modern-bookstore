@Test
 public void testActuatorEndpointsAccess() {
     mockMvc.perform(get("/actuator/health"))
         .andExpect(status().isForbidden());
     
     mockMvc.perform(get("/actuator/health"))
         .with(user("user").roles("ACTUATOR"))
         .andExpect(status().isOk());
 }
 
 @Test
 public void testNormalEndpointsAccess() {
     mockMvc.perform(get("/api/v1/auth/login"))
         .andExpect(status().isOk());
 }