package devgraft.support.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@ExtendWith(RestDocumentationExtension.class)
public class AbstractApiDocTest {
    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;
    @Autowired
    private ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected static final ResponseFieldsSnippet responseFields = responseFields(
            fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과 성공 여부"),
//            fieldWithPath("status").type(JsonFieldType.NUMBER).description("결과 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지"),
            fieldWithPath("timestamp").type(JsonFieldType.STRING).description("결과 시간(YYYY-MM-dd HH:mm:ss")
    );

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider, WebApplicationContext context) {
        document = MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(this.document)
                .build();
    }
}
