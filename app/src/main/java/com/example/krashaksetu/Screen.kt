package com.example.krashaksetu

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object LanguageSelection : Screen("language_selection")
    object Login : Screen("login")
    object CardInput : Screen("card_input")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object VendorManagement : Screen("vendor_management")
    object Shop : Screen("shop")
    object SupportDesk : Screen("support_desk")
    object AIAssistant : Screen("ai_assistant")
    object FinancialSupport : Screen("financial_support")
    object TestLabsReport : Screen("test_labs_report")
    object ProductListing : Screen("product_listing")
    object AddProductForm : Screen("add_product_form")
}