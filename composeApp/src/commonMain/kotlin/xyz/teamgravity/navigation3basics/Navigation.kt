package xyz.teamgravity.navigation3basics

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun Navigation() {
    val navigator = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.TodoList::class, Route.TodoList.serializer())
                    subclass(Route.Todo::class, Route.Todo.serializer())
                }
            }
        },
        Route.TodoList
    )
    NavDisplay(
        backStack = navigator,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.TodoList> {
                TodoListScreen(
                    onNavigateTodo = { todo ->
                        navigator.add(Route.Todo(todo))
                    }
                )
            }

            entry<Route.Todo> { route ->
                TodoScreen(
                    todoExtra = route.todoExtra
                )
            }
        }
    )
}