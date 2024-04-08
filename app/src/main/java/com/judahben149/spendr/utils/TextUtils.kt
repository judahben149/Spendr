package com.judahben149.spendr.utils

object TextUtils {

    private val budgetEntryHints = listOf(
        "E.g., Weekend getaway to the beach",
        "E.g., Grocery shopping for the week",
        "E.g., Birthday gift for best friend",
        "E.g., Coffee and croissant on the go",
        "E.g., New running shoes for training",
        "E.g., Concert tickets for upcoming show",
        "E.g., Tech gadget upgrade",
        "E.g., Dinner date at favorite restaurant",
        "E.g., Online course for personal growth",
        "E.g., Movie night snacks with friends",
        "E.g., Road trip gas and snacks",
        "E.g., Gardening supplies for balcony plants",
        "E.g., Designer bag splurge",
        "E.g., Pet grooming essentials",
        "E.g., Renewing magazine subscriptions",
        "E.g., Spa day for self-care",
        "E.g., Home decor for living room",
        "E.g., Camping gear for outdoor adventure",
        "E.g., Brunch with friends at trendy spot",
        "E.g., Investing in tech stocks",
        "E.g., Hair salon pampering",
        "E.g., Junk food",
        "E.g., Tech accessories for work",
        "E.g., New hobby supplies for painting",
        "E.g., Car maintenance and wash",
        "E.g., Fitness class membership",
        "E.g., Books for reading list",
        "E.g., DIY home improvement tools",
        "E.g., Travel savings fund",
        "E.g., TikTok merch for casual wear",
        "E.g., Fashion haul from online store",
        "E.g., Streaming service subscription",
        "E.g., Gaming console upgrade",
        "E.g., Skincare essentials for routine",
        "E.g., Sneaker collection addition",
        "E.g., Impromptu road trip essentials",
        "E.g., Stocking up on healthy snacks",
        "E.g., NFT purchase for digital art",
        "E.g., Sustainable fashion piece",
        "E.g., Social media marketing course",
        "E.g., Virtual concert ticket",
        "E.g., DIY room decor project",
        "E.g., Makeup essentials for everyday look",
        "E.g., Vegan meal delivery service",
        "E.g., Vintage vinyl record find",
        "E.g., Fitness influencer workout plan",
        "E.g., Youtube Premium Subscription",
        "E.g., Streetwear clothing piece",
        "E.g., Mindfulness app subscription",
        "E.g., New furniture for home office",
        "E.g., Photography equipment upgrade",
        "E.g., Duolingo subscription - French",
        "E.g., 2 Tickets for PL's \"Mistakenly Yours\"",
    )



    fun reasonRandomizer(): String {
        val randomIndex = budgetEntryHints.indices.random()
        return budgetEntryHints[randomIndex]
    }
}