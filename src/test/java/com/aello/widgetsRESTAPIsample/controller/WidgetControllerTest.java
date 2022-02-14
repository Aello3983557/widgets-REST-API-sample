package com.aello.widgetsRESTAPIsample.controller;

import com.aello.model.Widget;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static com.aello.constants.ControllerDocumentationConstants.WIDGET_MAPPING;
import static com.aello.widgetsRESTAPIsample.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class WidgetControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createWidgetWithAllProperties() throws Exception {
        Widget createCondition = createWidgetForTest();
        Widget responseCreateCondition = createWidgetByPostAction(createCondition);
        String widgetUuid = responseCreateCondition.getUuid();

        assertThat(widgetUuid).isNotNull();
        assertThat(createCondition.getX()).isEqualTo(responseCreateCondition.getX());
        assertThat(createCondition.getY()).isEqualTo(responseCreateCondition.getY());
        assertThat(createCondition.getZIndex()).isEqualTo(responseCreateCondition.getZIndex());
        assertThat(createCondition.getWidth()).isEqualTo(responseCreateCondition.getWidth());
        assertThat(createCondition.getHeight()).isEqualTo(responseCreateCondition.getHeight());
        assertThat(responseCreateCondition.getDateModified()).isNotNull();

        deleteAndCheckStatus("/" + widgetUuid, status().isOk());
    }

    @Test
    public void createWidgetWithUUID() throws Exception {
        Widget createCondition = createWidgetForTest();
        postAndCheckStatus(objectMapper.writeValueAsString(createCondition), status().isBadRequest());
    }

    @Test
    public void testUpdateWidget() throws Exception {
        Widget createCondition = createWidgetForTest();
        Widget responseCreateCondition = createWidgetByPostAction(createCondition);
        String widgetUuid = responseCreateCondition.getUuid();
        Widget updateCondition = createWidgetForTest();
        updateCondition.setUuid(widgetUuid);
        ResultActions updateResultAction = patchAndCheckStatus("/" + widgetUuid,
                objectMapper.writeValueAsString(updateCondition), status().isOk());
        Widget responseUpdateCondition = objectMapper.readValue(updateResultAction
                .andReturn()
                .getResponse()
                .getContentAsString(), Widget.class);

        assertThat(responseUpdateCondition.getUuid()).isNotNull();
        assertThat(updateCondition.getX()).isEqualTo(responseUpdateCondition.getX());
        assertThat(updateCondition.getY()).isEqualTo(responseUpdateCondition.getY());
        assertThat(updateCondition.getZIndex()).isEqualTo(responseUpdateCondition.getZIndex());
        assertThat(updateCondition.getWidth()).isEqualTo(responseUpdateCondition.getWidth());
        assertThat(updateCondition.getHeight()).isEqualTo(responseUpdateCondition.getHeight());

        deleteAndCheckStatus("/" + widgetUuid, status().isOk());
    }

    @Test
    public void testUpdateWidgetMismatchId() throws Exception {
        Widget updateCondition = createWidgetForTest();
        patchAndCheckStatus("/" + UUID.randomUUID(),
                objectMapper.writeValueAsString(updateCondition), status().isBadRequest());
    }

    @Test
    public void testUpdateWidgetNotFound() throws Exception {
        Widget updateCondition = createWidgetForTest();
        patchAndCheckStatus("/" + updateCondition.getUuid(),
                objectMapper.writeValueAsString(updateCondition), status().isNotFound());
    }

    @Test
    public void testDeleteWidget() throws Exception {
        Widget responseCreateCondition = createWidgetByPostAction(createWidgetForTest());
        deleteAndCheckStatus("/" + responseCreateCondition.getUuid(), status().isOk());
    }

    @Test
    public void testDeleteWidgetNotFound() throws Exception {
        deleteAndCheckStatus("/" + UUID.randomUUID(), status().isNotFound());
    }

    @Test
    public void testGetWidget() throws Exception {
        Widget createCondition = createWidgetForTest();
        Widget responseCreateCondition = createWidgetByPostAction(createCondition);
        String widgetUuid = responseCreateCondition.getUuid();
        Widget responseGetCondition = getWidgetByGetAction(widgetUuid);

        assertThat(widgetUuid).isEqualTo(responseGetCondition.getUuid());
        assertThat(createCondition.getX()).isEqualTo(responseGetCondition.getX());
        assertThat(createCondition.getY()).isEqualTo(responseGetCondition.getY());
        assertThat(createCondition.getZIndex()).isEqualTo(responseGetCondition.getZIndex());
        assertThat(createCondition.getWidth()).isEqualTo(responseGetCondition.getWidth());
        assertThat(createCondition.getHeight()).isEqualTo(responseGetCondition.getHeight());
        assertThat(responseGetCondition.getDateModified()).isNotNull();

        deleteAndCheckStatus("/" + widgetUuid, status().isOk());
    }

    @Test
    public void testGetWidgetNotFound() throws Exception {
        getAndCheckStatus("/" + UUID.randomUUID(), status().isNotFound());
    }

    @Test
    public void testGetWidgets() throws Exception {
        createWidgetByPostAction(createWidgetForTest());
        createWidgetByPostAction(createWidgetForTest());
        createWidgetByPostAction(createWidgetForTest());
        List<Widget> responseWidgetsGetCondition = getWidgetsByGetWidgetsAction();

        assertThat(3).isEqualTo(responseWidgetsGetCondition.size());
    }

    @Test
    public void testGetWidgetsNotFound() throws Exception {
        getAndCheckStatus(Strings.EMPTY, status().isNotFound());
    }

    private Widget createWidgetByPostAction(Widget createCondition) throws Exception {
        createCondition.setUuid(null);
        ResultActions createResultAction = postAndCheckStatus(objectMapper.writeValueAsString(createCondition), status().isCreated());
        return objectMapper.readValue(createResultAction
                .andReturn()
                .getResponse()
                .getContentAsString(), Widget.class);
    }

    private Widget getWidgetByGetAction(String widgetUuid) throws Exception {
        ResultActions resultAction = getAndCheckStatus("/" + widgetUuid, status().isOk());
        return objectMapper.readValue(resultAction
                .andReturn()
                .getResponse()
                .getContentAsString(), Widget.class);
    }

    private List<Widget> getWidgetsByGetWidgetsAction() throws Exception {
        ResultActions resultAction = getAndCheckStatus(Strings.EMPTY, status().isOk());
        return objectMapper.readValue(resultAction
                .andReturn()
                .getResponse()
                .getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Widget.class));
    }

    private ResultActions postAndCheckStatus(String requestBody, ResultMatcher resultMatcher) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .post(WIDGET_MAPPING)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(resultMatcher);
    }

    private ResultActions patchAndCheckStatus(String pathVariable, String requestBody, ResultMatcher resultMatcher) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .patch(WIDGET_MAPPING + pathVariable)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(resultMatcher);
    }

    private ResultActions getAndCheckStatus(String pathVariable, ResultMatcher resultMatcher) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .get(WIDGET_MAPPING + pathVariable))
                .andExpect(resultMatcher);
    }


    private void deleteAndCheckStatus(String pathVariable, ResultMatcher resultMatcher) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(WIDGET_MAPPING + pathVariable))
                .andExpect(resultMatcher);
    }
}
