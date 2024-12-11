package com.haunp.mybookstore.di

import androidx.room.Room
import com.haunp.mybookstore.data.database.db.AppDatabase
import com.haunp.mybookstore.data.repository.BookRepositoryImpl
import com.haunp.mybookstore.data.repository.CartRepositoryImpl
import com.haunp.mybookstore.data.repository.CategoryRepositoryImpl
import com.haunp.mybookstore.data.repository.OrderRepositoryImpl
import com.haunp.mybookstore.data.repository.UserRepositoryImpl
import com.haunp.mybookstore.domain.repository.IBookRepository
import com.haunp.mybookstore.domain.repository.ICartRepository
import com.haunp.mybookstore.domain.repository.ICategoryRepository
import com.haunp.mybookstore.domain.repository.IOrderRepository
import com.haunp.mybookstore.domain.repository.IUserRepository
import com.haunp.mybookstore.domain.usecase.AddBookUseCase
import com.haunp.mybookstore.domain.usecase.AddCateUseCase
import com.haunp.mybookstore.domain.usecase.DeleteCateUseCase
import com.haunp.mybookstore.domain.usecase.GetAccountUseCase
import com.haunp.mybookstore.domain.usecase.GetBookByCateIDUseCase
import com.haunp.mybookstore.domain.usecase.GetCateUseCase
import com.haunp.mybookstore.domain.usecase.GetListBookUseCase
import com.haunp.mybookstore.domain.usecase.LoginUseCase
import com.haunp.mybookstore.domain.usecase.RegisterUseCase
import com.haunp.mybookstore.presenters.fragment.admin.book.BookViewModel
import com.haunp.mybookstore.presenters.fragment.admin.category_admin.CategoryAdminViewModel
import com.haunp.mybookstore.presenters.fragment.admin.statistical.StatisticalViewModel
import com.haunp.mybookstore.presenters.fragment.admin.user.UserViewModel
import com.haunp.mybookstore.presenters.fragment.login.LoginViewModel
import com.haunp.mybookstore.presenters.fragment.register.RegisterViewModel
import com.haunp.mybookstore.presenters.fragment.user.cart.CartViewModel
import com.haunp.mybookstore.presenters.fragment.user.category_user.CategoryUserViewModel
import com.haunp.mybookstore.presenters.fragment.user.home.HomeViewModel
import com.haunp.mybookstore.presenters.fragment.user.search.SearchViewModel
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            databaseModule
        )
    }

var viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { BookViewModel(get(),get()) }
    viewModel { UserViewModel(get(),get()) }
    viewModel { CategoryAdminViewModel(get(),get(),get()) }
    viewModel { StatisticalViewModel() }
    viewModel { CategoryUserViewModel(get(),get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SettingViewModel() }
    viewModel { CartViewModel() }
}


var useCaseModule = module {
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { AddCateUseCase(get()) }
    factory { AddBookUseCase(get()) }
    factory { GetListBookUseCase(get()) }
    factory { GetCateUseCase(get()) }
    factory { GetAccountUseCase(get()) }
    factory { DeleteCateUseCase(get()) }
    factory { GetBookByCateIDUseCase(get()) }
}

var repositoryModule = module {
    single<IUserRepository> { UserRepositoryImpl(get(),get()) }
    single<IBookRepository>{ BookRepositoryImpl(get()) }
    single<ICategoryRepository>{ CategoryRepositoryImpl(get()) }
    single<ICartRepository> { CartRepositoryImpl(get()) }
    single<IOrderRepository> { OrderRepositoryImpl(get()) }
}

val databaseModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "app_database").build() }
    single { get<AppDatabase>().getUserDao() }
    single { get<AppDatabase>().getBookDao() }
    single { get<AppDatabase>().getCategoriesDao() }
    single { get<AppDatabase>().getCartDao() }
    single { get<AppDatabase>().getOrderDao() }
    single { get<AppDatabase>().getRateDao() }
}

