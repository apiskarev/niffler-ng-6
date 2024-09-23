package guru.qa.niffler.jupiter.annotation;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;
import lombok.val;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoryExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(CategoryExtension.class);

    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    public void beforeEach(ExtensionContext context) {
        val faker = new Faker();

        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
                .ifPresent(a -> {
                    // случайное название категории при создании
                    val randomName = a.name().isEmpty() ? faker.cat().name() : a.name();

                    CategoryJson categoryJson = new CategoryJson(
                            null,
                            randomName,
                            a.username(),
                            false);

                    // создание категории через api
                    var createdCategory = spendApiClient.addCategory(categoryJson);

                    // добавление в категорию archived = true если категория должна быть архивированной
                    if (a.archived()){
                        val archivedCategoryJson = new CategoryJson(
                                createdCategory.id(),
                                createdCategory.name(),
                                createdCategory.username(),
                                true
                        );

                        createdCategory = spendApiClient.updateCategory(archivedCategoryJson);
                    }

                    context.getStore(NAMESPACE).put(context.getUniqueId(), createdCategory);
                });
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        val category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);

        // архивация категории после теста в случае если она не архивирована
        if (!category.archived()){
            val archivedCategoryJson = new CategoryJson(
                    category.id(),
                    category.name(),
                    category.username(),
                    true
            );
            spendApiClient.updateCategory(archivedCategoryJson);
        }
    }

    public static class CategoryResolverExtension implements ParameterResolver {

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws
                ParameterResolutionException {

            return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws
                ParameterResolutionException {

            return extensionContext.getStore(CategoryExtension.NAMESPACE)
                    .get(extensionContext.getUniqueId(), CategoryJson.class);
        }
    }

}
