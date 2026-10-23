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

public class InsecureDesignTask1Test extends LessonTest {

  @Test
  void normalPriceDoesNotComplete() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task1")
                .param("productName", "WebGoat Widget")
                .param("price", "29.99"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(false)));
  }

  @Test
  void zeroPriceCompletes() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task1")
                .param("productName", "WebGoat Widget")
                .param("price", "0.0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(true)));
  }

  @Test
  void negativePriceCompletes() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/InsecureDesign/task1")
                .param("productName", "WebGoat Widget")
                .param("price", "-99.99"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lessonCompleted", is(true)));
  }
}
