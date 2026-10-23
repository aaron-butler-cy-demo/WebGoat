/*
 * SPDX-FileCopyrightText: Copyright © 2024 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.insecuredesign;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.owasp.webgoat.container.plugins.LessonTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class InsecureDesignTask2Test extends LessonTest {

  @Test
  void positiveQuantityDoesNotComplete() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task2").param("quantity", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)));
  }

  @Test
  void zeroQuantityDoesNotComplete() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task2").param("quantity", "0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)));
  }

  @Test
  void negativeQuantityCompletes() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task2").param("quantity", "-1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(true)));
  }

  @Test
  void largeNegativeQuantityCompletes() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task2").param("quantity", "-100"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(true)));
  }
}
