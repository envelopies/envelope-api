package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.envelope.api.dto.category.CategoryDto
import ru.envelope.api.dto.category.CategoryPostDto
import ru.envelope.api.dto.category.CategoryPutDto
import ru.envelope.api.dto.category.CategoryTreeNodeDto
import ru.envelope.api.services.CategoryService
import java.util.*

@Tag(name = "category (v1)", description = "работа с категориями товаров")
@RestController
@RequestMapping(value = ["v1/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CategoryControllerV1(
    private val categoryService: CategoryService
) {
    @Operation(summary = "получение всего списка категорий")
    @GetMapping
    fun getCategories(
        @RequestParam("pageNumber", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = "10") pageSize: Int,
        @RequestParam("sortField", required = false, defaultValue = "title") sortField: String,
        @RequestParam("sortDirection", required = false, defaultValue = "ASC") sortDirection: Sort.Direction,
    ): List<CategoryDto> {
        return categoryService.getCategories(pageNumber, pageSize, sortField, sortDirection)
    }

    @Operation(summary = "получение всего списка категорий в виде дерева",
        description = "родительских категорий может быть много, поэтому возвращается список")
    @GetMapping("tree")
    fun getCategoriesTree(): List<CategoryTreeNodeDto> {
        return categoryService.getCategoriesTree()
    }

    @Operation(summary = "получение всей информации о категории")
    @GetMapping("{id}")
    fun getItem(
        @PathVariable("id") id: UUID
    ): ResponseEntity<CategoryDto> {
        val itemCategory = categoryService.getCategory(id)

        return if (itemCategory != null) {
            ResponseEntity.ok(itemCategory)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @Operation(summary = "создание новой категории")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createItemCategory(
        @RequestBody categoryDto: CategoryPostDto
    ): ResponseEntity<CategoryDto> {
        val category = categoryService.createCategory(categoryDto)
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path(category.id)
            .build()
            .toUri()
        return ResponseEntity.created(uri).body(category)
    }

    @Operation(summary = "изменение категории")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "default")
    @PutMapping("{id}")
    fun updateItemCategory(
        @PathVariable("id") id: UUID,
        @RequestBody categoryDto: CategoryPutDto
    ): CategoryDto {
        return categoryService.updateCategory(id, categoryDto)
    }

    @Operation(summary = "удаление категории")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteItemCategory(
        @PathVariable("id") id: UUID
    ) {
        categoryService.deleteCategory(id)
    }
}
