package co.devskills.springbootboilerplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.devskills.springbootboilerplate.dto.RequestTodo;
import co.devskills.springbootboilerplate.entity.TodoEntity;
import co.devskills.springbootboilerplate.error.NotFoundException;
import co.devskills.springbootboilerplate.repository.TodoRepository;
import co.devskills.springbootboilerplate.dto.UpdateTodoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;

    @Transactional(readOnly = true) 
    public TodoEntity getTodo(Long id){
        return getOrThrow(id);
    }

    @Transactional
    public TodoEntity save(RequestTodo requestTodo){
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setName(requestTodo.name());
        todoEntity.setDescription(requestTodo.description());
        todoEntity.setStatus(requestTodo.status());
        return todoRepository.save(todoEntity);
    }

    @Transactional(readOnly = true)
    public Page<TodoEntity> getTodos(Pageable pageable){
        return todoRepository.findAll(pageable);
    }

    @Transactional
    public TodoEntity updateTodo(Long id, UpdateTodoRequest updateTodoRequest){
        TodoEntity todoEntity = getOrThrow(id);
        todoEntity.update(updateTodoRequest.name(), updateTodoRequest.description(), updateTodoRequest.status());
        return todoEntity;     
    }

    @Transactional
    public void deleteTodo(Long id){
        TodoEntity todoEntity = getOrThrow(id);
        todoRepository.delete(todoEntity);  
    }

    private TodoEntity getOrThrow(Long id) {
        return todoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Todo not found"));
    }
}